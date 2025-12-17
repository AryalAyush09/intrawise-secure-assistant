package com.intrawise;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IntraWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntraWiseApplication.class, args);
	}
	
	
	  @Bean
	    ApplicationRunner verify(ChatModel chatModel) {
	        return args -> {
	            System.out.println("ChatModel bean = " + chatModel.getClass());
	        };
	    }
}
