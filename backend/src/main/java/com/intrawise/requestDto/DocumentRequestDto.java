package com.intrawise.requestDto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentRequestDto {
  private String title;
  private MultipartFile[] file;
}
