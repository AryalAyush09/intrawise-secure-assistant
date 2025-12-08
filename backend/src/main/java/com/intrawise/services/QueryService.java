package com.intrawise.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.intrawise.entities.User;
import com.intrawise.exception.ResourceNotFoundException;
import com.intrawise.repository.UserRepository;
import com.intrawise.requestDto.QueryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service 
@Slf4j
@Builder
public class QueryService {
  private final UserRepository userRepo;
  private final DocumentEmbeddingService embeddingService;
  
	public float[] embedAskQuery(Long userId, QueryDTO dto) {
		
		  Optional<User> optionalUser = userRepo.findById(userId);
		   if(optionalUser.isEmpty()) {
			   log.warn("User not found with ID:{}", userId);
			   throw new ResourceNotFoundException("User not found");
		   }
		   
		   return embeddingService.embeddedQuery(dto.getQuestions());
	}
}
