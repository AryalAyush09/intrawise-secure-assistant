package com.intrawise.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.intrawise.Enum.Role;
import com.intrawise.entities.Document;
import com.intrawise.entities.User;
import com.intrawise.exception.BadRequestException;
import com.intrawise.exception.FileUploadException;
import com.intrawise.exception.ResourceNotFoundException;
import com.intrawise.repository.DocumentRepository;
import com.intrawise.repository.UserRepository;
import com.intrawise.requestDto.DocumentRequestDto;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawise.util.FileParseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Builder
public class DocumentService {
	private final UserRepository userRepo;
	private final DocumentRepository docRepo;
	private final FileStorageService fileService;
	private final FileParseUtil fileUtil;
	private final DocumentEmbeddingService embeddingService;
	
   public ApiResponse<?> uploadDocument(Long userId , DocumentRequestDto dto){
	   log.info("Attempting to upload document for userId: {}", userId);
	 
	   Optional<User> optionalUser = userRepo.findById(userId);
	   if(optionalUser.isEmpty()) {
		   log.warn("User not found with ID:{}", userId);
		   throw new ResourceNotFoundException("User not found");
	   }
	   
	   User user = optionalUser.get();
	   Role role = user.getRole();

	   log.info("DB user role: '{}' (class: {})", role, role != null ?
			   role.getClass().getName() : "null"); 

	   if ((!role.equals(Role.ADMIN)) && (!role.equals(Role.HR))){
		   log.warn("Access denied for user role :{}", role);
		   throw new BadRequestException("Only ADMIN and HR are allowed to upload documents.");
	   }  
	   
	   MultipartFile[] files = dto.getFile();
	   
	   if(files == null || files.length == 0) {
		    throw new FileUploadException("No files uploaded");
	   }
	   
	   for(MultipartFile file :files) {
		   String filePath = fileService.storeFile(file);
		   String content = fileUtil.extractText(file);
		   String extractedTitle = fileUtil.extractTitle(file);
		   String finalTitle = (dto.getTitle() != null && !dto.getTitle().isBlank()) ? 
				   dto.getTitle() : extractedTitle;
		   log.info("Document title used: {}", finalTitle);
		   
		   System.out.println("content class = " + content.getClass().getName());
		   System.out.println("content length = " + content.length());

		   Document document = Document.builder()
				   .title(finalTitle)
				   .uploadedBy(user)
				   .originalFileName(file.getOriginalFilename())
				   .fileType(file.getContentType())
				   .filePath(filePath)
				   .content(content)
				   .roleAllowed(user.getRole())
				   .build();
		   
		  Document saved = docRepo.save(document);
		   log.info("File Saved:{}", filePath);
		   
		   embeddingService.processDocument(saved);
		   	
	   }
	   
	   return new ApiResponse<>(true, "Uploaded successfully", null);
   }
}
