package com.intrawise.services;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LLMService {
	private final HuggingfaceChatModel chatModel;
     
     public String getAnswer(String context, String query){
    	 String finalPrompt = """ 
    	 		
    	 		 %s 
                // user question
    			 
    			 Provide the best possible answer based ONLY on the context.
    			 
    			 """.formatted(context, query);
    	 
    	  UserMessage msg = new UserMessage(finalPrompt);

          String response = chatModel.call(msg);

          return response;
     }
}
