package com.intrawise.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.intrawise.entities.User;
import com.intrawise.repository.UserRepository;
import com.intrawise.requestDto.RegisterRequestDTO;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawsie.exception.BadRequestException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
	private final UserRepository userRepo;
//	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public ApiResponse<?> registerUser(RegisterRequestDTO dto){
		
		if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
		
		User user = User.builder()
				.fullName(dto.getFullName())
				.email(dto.getEmail())
//				.password(passwordEncoder.encode(dto.getPassword()))
				.role(dto.getRole())
				.build();
		
		userRepo.save(user);
		 
		return null;
	}
}
