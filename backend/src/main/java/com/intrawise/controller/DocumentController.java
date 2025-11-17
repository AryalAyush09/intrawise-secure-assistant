package com.intrawise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intrawise.entities.Document;
import com.intrawise.repository.DocumentRepository;
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
	private final DocumentRepository docRepo;
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")

	// Just for testing
//	@PreAuthorize("permitAll()")
	
	@PostMapping("/upload")
	
	public ResponseEntity<ApiResponse<?>> uploadDocument
	             (Authentication auth, @Valid @ModelAttribute DocumentRequestDto dto){
		 System.out.println("Received files DTO: " + dto.getFile());
		    if (dto.getFile() != null) {
		        System.out.println("📁 File count: " + dto.getFile().length);
		        for (MultipartFile f : dto.getFile()) {
		            System.out.println(" File name: " + f.getOriginalFilename());
		            System.out.println(" Size = " + f.getSize());
		            System.out.println(" Type = " + f.getContentType());
		        }
		    } else {
		        System.out.println("❌ dto.getFile() is null");
		    }

		Long userId = Long.parseLong(auth.getName());
		ApiResponse<?> response = documentService.uploadDocument(userId, dto);
		return ResponseEntity.ok(response);
	}	
	
	@GetMapping("/test/{id}")
	public String testFetch(@PathVariable Long id) {
	    Document doc = docRepo.findById(id).orElseThrow();
	    return doc.getContent(); // return content directly
	}

}
