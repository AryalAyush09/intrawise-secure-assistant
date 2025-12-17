//package com.intrawise.services;
//
//import org.springframework.stereotype.Service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.ai.chat.model.ChatModel;
//
//import org.springframework.ai.chat.messages.UserMessage;
//
//@Service
//@AllArgsConstructor
//public class LLMService {
//	
//	private final ChatModel chatModel;
//     
//     public String getAnswer(String context, String query){
//    	 String finalPrompt = """
//    			    %s
//    			    User question: %s
//
//    			    Provide the best possible answer based ONLY on the context.
//    			""".formatted(context, query);
//    	 
//    	  UserMessage msg = new UserMessage(finalPrompt);
//
//          String response = chatModel.call(msg);
//
//          return response;
//     }
//}


package com.intrawise.services;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LLMService {

    private final ChatModel chatModel;

    public String getAnswer(String context, String query) {

        String finalPrompt = """
                %s

                User question: %s

                Provide the best possible answer based ONLY on the context.
                """.formatted(context, query);

        UserMessage message = new UserMessage(finalPrompt);

        // chatModel.call() returns the assistant's response directly as a String
        return chatModel.call(message);
    }
}



