package com.intrawise.entities;

import java.time.LocalDateTime;

import com.intrawise.Enum.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rag_query_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagQueryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role userRole;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(columnDefinition = "jsonb")
    private String retrievedChunks; // JSON string of top chunks

    @Column(columnDefinition = "jsonb")
    private String similarityScores; // JSON string of distances

    private LocalDateTime createdAt;
}
