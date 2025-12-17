//package com.intrawise.configuration;
//
//import org.springframework.ai.chat.ChatModel;
//import org.springframework.ai.huggingface.HuggingfaceChatModel;
//import org.springframework.ai.huggingface.HuggingfaceChatOptions;
//import org.springframework.ai.huggingface.api.HuggingfaceApi;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class DebugConfig {
//
//    @Value("${spring.ai.huggingface.api-key}")
//    private String apiKey;
//
//    @Value("${spring.ai.huggingface.url}")
//    private String chatUrl;
//
//    @Bean
//    @Primary  // Overrides the auto-configured bean
//    public ChatModel chatModel() {
//        // Explicitly build the API with key and URL — no fallbacks
//        HuggingfaceApi huggingfaceApi = new HuggingfaceApi(apiKey, chatUrl);
//        
//        // Options from your properties
//        HuggingfaceChatOptions options = HuggingfaceChatOptions.builder()
//                .withTemperature(0.1f)
//                .withMaxTokens(512)
//                .withWaitForModel(true)
//                .build();
//
//        return new HuggingfaceChatModel(huggingfaceApi, options);
//    }
//}