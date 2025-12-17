package com.intrawise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intrawise.requestDto.QueryDTO;
import com.intrawise.responseDTO.ApiResponse;
import com.intrawise.responseDTO.RagResponse;
import com.intrawise.services.LLMService;
import com.intrawise.services.QueryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/query")
public class QueryController {
	
   private final QueryService queryService;
	private LLMService llmService;
   
	@PostMapping("/ask")
	public ResponseEntity<ApiResponse<?>> ask(Authentication auth, 
			        @RequestBody QueryDTO req ) {
		
		Long userId = Long.parseLong(auth.getName());

	    RagResponse ragResponse = queryService.executeRAG(
	    		userId, req.getQuestions() );

	    ApiResponse<?> apiRes = new ApiResponse<>(
	            true,
	            "RAG query executed successfully",
	            ragResponse
	    );

	    return ResponseEntity.ok(apiRes);
	}
	
	  @PostMapping("/check")
	    public String ask(@RequestParam String context,  @RequestBody QueryDTO req) {
	        return llmService.getAnswer(context, req.getQuestions());
	    }
}
