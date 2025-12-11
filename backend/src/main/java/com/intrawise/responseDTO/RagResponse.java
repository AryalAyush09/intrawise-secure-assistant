package com.intrawise.responseDTO;


import java.util.List;

import com.intrawise.requestDto.SimilarChunk;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RagResponse {
    private String answer;
    private List<SimilarChunk> sources;
}
