package com.intrawise.responseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RagResponse {
    private String answer;
//    private List<SimilarChunk> sources;
}
