package com.intrawise.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;

@Configuration
public class AiConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        return new TransformersEmbeddingModel();  // NO args: uses default model all‑MiniLM‑L6‑v2
    }
}
