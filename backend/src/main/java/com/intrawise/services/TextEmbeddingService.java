package com.intrawise.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TextEmbeddingService {
	
	    private final EmbeddingModel embeddingModel;
	
    public float[] embeddedQuery(String query) {
    	
     if (query == null || query.isBlank()) {
              throw new IllegalArgumentException("Question cannot be empty");
          }
    
      String cleanQuery = query.trim().toLowerCase();
    
     return embeddingModel.embed(cleanQuery);

    }
	
}
