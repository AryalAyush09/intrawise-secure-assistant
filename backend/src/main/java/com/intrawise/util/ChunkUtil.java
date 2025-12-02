package com.intrawise.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChunkUtil {
     public List<String> chunkText(String content, int chunkSize){
    	 List<String> chunks = new ArrayList<>();
    	 
    	 for (int i = 0; i < content.length(); i += chunkSize) {
             int end = Math.min(content.length(), i + chunkSize);
             String chunk = content.substring(i, end).trim();
             if (!chunk.isBlank()) chunks.add(chunk);
         }
    	 return chunks;
     }
}
