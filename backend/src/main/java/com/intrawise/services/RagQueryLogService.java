package com.intrawise.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrawise.Enum.Role;
import com.intrawise.entities.RagQueryLog;
import com.intrawise.repository.RagQueryLogRepository;
import com.intrawise.requestDto.SimilarChunk;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RagQueryLogService {

    private final RagQueryLogRepository logRepo;
    private final ObjectMapper objectMapper;

    public void saveLog(Long userId, Role userRole, String query, String answer,
    		List<SimilarChunk> chunks) {

        try {
             String chunksJson = objectMapper.writeValueAsString(
                    chunks.stream()
                    .map(SimilarChunk::getChunkText).collect(Collectors.toList()));

            String scoresJson = objectMapper.writeValueAsString(
                    chunks.stream().map(SimilarChunk::getDistance)
                    .collect(Collectors.toList()));

        RagQueryLog log = RagQueryLog.builder()
                    .userId(userId)
                    .userRole(userRole)
                    .question(query)
                    .answer(answer)
                    .retrievedChunks(chunksJson)
                    .similarityScores(scoresJson)
                    .createdAt(LocalDateTime.now())
                    .build();

            logRepo.save(log);

        } catch (JsonProcessingException e) {
             e.printStackTrace();
        }
    }
}
