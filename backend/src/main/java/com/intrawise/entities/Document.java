package com.intrawise.entities;

import java.time.LocalDateTime;

import com.intrawise.Enum.Role;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String title;
  
  @Lob
  @Column(columnDefinition = "TEXT")
  private String content;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User uploadedBy;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role roleAllowed;  // reuse your existing Role enum
  
  private String fileType; // e.g. "pdf", "docx"
  private String originalFileName;
  
  @Column(name = "file_path")
  private String filePath;

  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  @PrePersist
  protected void onCreate() {
      this.createdAt = LocalDateTime.now();
  }
}
