package com.intrawise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intrawise.requestDto.LoginRequestDTO;
import com.intrawise.requestDto.RegisterRequestDTO;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawise.services.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
   private final AuthService authService;
   
   @PreAuthorize("hasRole('ADMIN')or hasRole('HR')")
   @PostMapping("/register")
   public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody RegisterRequestDTO dto){
	   ApiResponse<?> response = authService.registerUser(dto);
	   return ResponseEntity.ok(response);
   }
   
   @PostMapping("/login")
   public ResponseEntity<ApiResponse<?>> loginUser(@Valid @RequestBody LoginRequestDTO dto){
	   ApiResponse<?> response = authService.login(dto);
	   return ResponseEntity.ok(response);
   }
}
