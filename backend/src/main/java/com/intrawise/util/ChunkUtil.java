package com.intrawise.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChunkUtil {
     public List<String> chunkTextWithOverLap(String content, int chunkSize, int overLap){
    	 List<String> chunks = new ArrayList<>();
    	 
    	 if(overLap >= chunkSize) {
    		 throw new IllegalArgumentException("Overlap size must be smaller than chunksie");
    	 }
    	 int start = 0;
    	 int length = content.length();
    	 while(start < length) {
    		 int end  = Math.min(start + chunkSize,length);
    		 
    		 String chunk = content.substring(start, end).trim();
    		 
    		 if(!chunk.isBlank()) {
    			 chunks.add(chunk);
    		 }
    		 
    		 start = start + chunkSize - overLap;
    	 }
    	 return chunks;
     }	
}
