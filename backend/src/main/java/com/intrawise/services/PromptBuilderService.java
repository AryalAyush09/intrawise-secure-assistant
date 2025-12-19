package com.intrawise.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intrawise.DTO.RagPrompt;
import com.intrawise.requestDto.SimilarChunk;

@Service
public class PromptBuilderService {
    private static final String RAG_RULES = """ 
    		You are an entripise assistants . You must only answer onlly using contect provided.
    		If the answer is not present in the context respond with 
    		"Your query is out of the company policy" Donot guess your own.
    		""";
    
    public RagPrompt buildRagPrompt(String query, List<SimilarChunk> chunks) {
    	StringBuilder context = new StringBuilder();
    	
        context.append(" RAG CONTEXT\n");
        
    	int index = 1;
    	
    	for(SimilarChunk chunk : chunks) {
    		context.append("Chunk ").append(index++).append(":\n");
            context.append(chunk.getChunkText()).append("\n\n");
    	}
    	
    	RagPrompt prompt = RagPrompt.builder()
    			.context(RAG_RULES + "\n\n" + context)
    			.question(query)
    			.build();
    	
    	return prompt;
    }   
}
