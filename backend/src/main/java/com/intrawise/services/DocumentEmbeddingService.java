package com.intrawise.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import com.intrawise.entities.Document;
import com.intrawise.entities.DocumentChunk;
import com.intrawise.repository.DocumentChunkRepository;
import com.intrawise.util.ChunkUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentEmbeddingService {
	
	private final EmbeddingModel embeddingModel;
	private final ChunkUtil chunkUtil;
	private final DocumentChunkRepository documentChunkRepo;
	 
     @Transactional
      public void processDocument(Document document) {
    	 List<String> chunks = chunkUtil.chunkTextWithOverLap(document.getContent(), 800, 120);
    	 List<DocumentChunk> chunkEntities = new ArrayList<>();
    	 
    	 int index = 0;
    	 
    	 for(String c : chunks) {
    		 
    		 if (c.isBlank()) continue;
    		 
    		 float[] embedding = embeddingModel.embed(c);
    		 
    		 DocumentChunk chunk = DocumentChunk.builder()
    				 .document(document)
    				 .chunkText(c)
    				 .chunkIndex(index++)
    				 .roleAllowed(document.getRoleAllowed())
    				 .embedding(embedding)
    				 .build();
    		 
    		 chunkEntities.add(chunk);
    	 }
    	 documentChunkRepo.saveAll(chunkEntities);
     }
         
//     public float[] generateEmbedding(String text) {
//    	    return embeddingModel.embed(text);
//    	}
}
