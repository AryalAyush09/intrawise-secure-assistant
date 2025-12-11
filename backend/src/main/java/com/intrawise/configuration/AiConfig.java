package com.intrawise.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;

@Configuration
public class AiConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        return new TransformersEmbeddingModel();  // NO args: uses default model all‑MiniLM‑L6‑v2
    }
  
//    @Bean
//    public HuggingfaceChatModel chatModel() {
//        return new HuggingfaceChatModel(
//                "meta-llama/Llama-3.1-8B-Instruct",  // model ID
//                System.getenv("HUGGINGFACE_TOKEN")   // API key
//        );
//    }
    
    
    @Bean
    public HuggingfaceChatModel chatModel() {
        String apiKey = System.getenv("HUGGINGFACE_TOKEN");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("HUGGINGFACE_TOKEN environment variable is missing");
        }

        // Direct constructor usage
        return new HuggingfaceChatModel("meta-llama/Llama-3.1-8B-Instruct", apiKey);
    }
}
