package com.intrawise.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intrawise.DTO.RagPrompt;
import com.intrawise.requestDto.SimilarChunk;

@Service
public class PromptBuilderService {
	private static final String RAG_RULES = """
			You are an enterprise internal assistant.
			Answer STRICTLY using the provided context.
			If the answer is not present, reply exactly:
			"Your query is out of the company policy."
			Do not use any external knowledge.
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
