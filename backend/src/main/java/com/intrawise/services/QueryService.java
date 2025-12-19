package com.intrawise.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.intrawise.DTO.RagPrompt;
import com.intrawise.Enum.Role;
import com.intrawise.entities.User;
import com.intrawise.exception.ResourceNotFoundException;
import com.intrawise.repository.DocumentChunkRepository;
import com.intrawise.repository.UserRepository;
import com.intrawise.requestDto.SimilarChunk;
import com.intrawise.responseDTO.RagResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service 
@Slf4j
@Builder
public class QueryService {
	
  private final UserRepository userRepo;
  private final TextEmbeddingService embeddingService;
  private final DocumentChunkRepository chunkRepo;
  private final PromptBuilderService promptService;
  private final LLMService llmService;
  private final RagQueryLogService ragQueryLogService;
  
     //step 1
	public float[] embedUserQuery(String question) {
		   return embeddingService.embeddedQuery(question);
	}
	
	//step 	2
	public List<SimilarChunk> searchRelvantChunks(Role role, float[] embedding){
		List<Object[]> rows = chunkRepo.searchSimilarChunksRaw(embedding, 5);
		
		List<SimilarChunk> results = new ArrayList<>();
		
		for (Object[] row : rows) {
	        String text = (String) row[0];          // chunk_text
	        int chunkIndex = ((Number) row[3]).intValue(); // chunk_index
	        Long docId = ((Number) row[4]).longValue();    // document_id
	        float distance = ((Number) row[5]).floatValue(); // similarity_score

	        results.add(new SimilarChunk(null, text, distance, docId, chunkIndex));
	    }
        return results;
    }
	
	public RagResponse executeRAG (Long userId, String query) {
		  Optional<User> optionalUser = userRepo.findById(userId);
		   if(optionalUser.isEmpty()) {
			   log.warn("User not found with ID:{}", userId);
			   throw new ResourceNotFoundException("User not found");
		   }
		   
		   User user = optionalUser.get();
		   Role role = user.getRole();
		   
		   float [] embedding = embedUserQuery(query);
		   
		   List<SimilarChunk> chunks = searchRelvantChunks(role, embedding);
		   
		   RagPrompt prompt = promptService.buildRagPrompt(query, chunks); 
		   
		   String answer = llmService.getAnswer(prompt.getContext(), prompt.getQuestion());
		   
		   ragQueryLogService.saveLog(userId, role, query, answer, chunks);
		   
		   return new RagResponse(answer , chunks);
	}
}
