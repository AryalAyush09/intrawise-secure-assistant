package com.intrawise.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimilarChunk {
    private Long id;
    private String chunkText;
    private float distance;     // similarity score
    private Long documentId;
    private int chunkIndex;
}
