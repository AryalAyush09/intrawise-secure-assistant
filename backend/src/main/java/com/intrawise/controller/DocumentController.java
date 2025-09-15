package com.intrawise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intrawise.requestDto.DocumentRequestDto;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawise.services.DocumentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/document")
@AllArgsConstructor
public class DocumentController {
	private final DocumentService documentService;
	
	@PreAuthorize("hasRole('ADMIN')or hasRole('HR')")
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse<?>> uploadDocument
	             (Authentication auth, @Valid @ModelAttribute DocumentRequestDto dto){
		
		Long userId = Long.parseLong(auth.getName());
		ApiResponse<?> response = documentService.uploadDocument(userId, dto);
		return ResponseEntity.ok(response);
	}
}
