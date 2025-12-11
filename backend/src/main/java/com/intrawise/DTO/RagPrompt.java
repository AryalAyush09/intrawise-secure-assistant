package com.intrawise.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RagPrompt {
    private String context;
    private String question;
}
