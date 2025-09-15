package com.intrawise.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.intrawise.Enum.EmailStatus;
import com.intrawise.Enum.Role;
import com.intrawise.JWT.JwtUtil;
import com.intrawise.entities.User;
import com.intrawise.exception.BadRequestException;
import com.intrawise.exception.ResourceNotFoundException;
import com.intrawise.repository.UserRepository;
import com.intrawise.requestDto.LoginRequestDTO;
import com.intrawise.requestDto.RegisterRequestDTO;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawise.responseDTO.LoginResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@Transactional
	public ApiResponse<?> registerUser(RegisterRequestDTO dto){
		
		if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
		
		User user = User.builder()
				.fullName(dto.getFullName())
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.role(dto.getRole())
				.status(EmailStatus.VERIFIED)
				.build();
		
		userRepo.save(user);
		 
		return new ApiResponse<>(true, "Register succesffully", null);
	}
	
	@Transactional
	 public ApiResponse<?> login(LoginRequestDTO dto){
		
	   log.info("Login attempt for {}", dto.getEmail());
	   
		User user = userRepo.findByEmail(dto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Email not registerted"));
		
		if(!passwordEncoder.matches(dto.getPassword(),user.getPassword())) {
			return new ApiResponse<>(false, "Invalid Credntails", null);
		}
		
		validateEmailVerification(user);
		
		String token = jwtUtil.generateToken(user.getId(), user.getRole());
		
        LoginResponseDTO response = LoginResponseDTO.builder()
        		.fullName(user.getFullName())
        		.role(user.getRole())
        		.token(token)
        		.build();
        
        return new ApiResponse<>(true, "LoginSuccesfully", response);
  }
	
	private void validateEmailVerification(User user) {
	    List<Role> rolesThatNeedVerification = List.of(Role.USER, Role.IT, Role.EMPLOYEE);

	    if (rolesThatNeedVerification.contains(user.getRole()) &&
	        user.getStatus() != EmailStatus.VERIFIED) {
	        throw new BadRequestException("Email not verified");
	    }

	    if (!List.of(Role.USER, Role.IT, Role.EMPLOYEE, Role.HR, Role.ADMIN).contains(user.getRole())) {
	        throw new BadRequestException("Unknown role assigned");
	    }
	}

}
