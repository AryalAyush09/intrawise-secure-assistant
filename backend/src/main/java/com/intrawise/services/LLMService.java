
package com.intrawise.services;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LLMService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/chat";
    private static final String MODEL = "phi3";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAnswer(String context, String query) {

        String finalPrompt = """
                %s

                User Question:
                %s

                Answer ONLY using the above context.
                """.formatted(context, query);

        Map<String, Object> body = new HashMap<>();
        body.put("model", MODEL);
        body.put("stream", false);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "user",
                "content", finalPrompt
        ));

        body.put("messages", messages);

        Map response = restTemplate.postForObject(
                OLLAMA_URL,
                body,
                Map.class
        );

        Map message = (Map) response.get("message");
        return message.get("content").toString();
    }
}
