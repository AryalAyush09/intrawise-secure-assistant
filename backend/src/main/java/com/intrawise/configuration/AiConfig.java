package com.intrawise.configuration;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        return new TransformersEmbeddingModel();  // NO args: uses default model all‑MiniLM‑L6‑v2
 
    }

//    @Bean
//    public HuggingfaceChatModel chatModel() {
//        String apiKey = System.getenv("HUGGINGFACE_TOKEN");
//        if (apiKey == null || apiKey.isBlank()) {
//            throw new IllegalStateException("HUGGINGFACE_TOKEN environment variable is missing");
//        }
//        apiKey = apiKey.trim();
//
//        // Use the full API URL
//        String modelUrl = "https://api-inference.huggingface.co/models/meta-llama/Llama-3.1-8B-Instruct";
//        System.out.println("Using Hugging Face API URL: " + modelUrl);
//        System.out.println("Using API key: " + apiKey.substring(0, 5) + "..."); // partial for security
//
//        return new HuggingfaceChatModel(modelUrl, apiKey);
//    }

}
