package com.intrawise.services;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath); // create folder if not exists

            String originalFileName = Path.of(file.getOriginalFilename()).getFileName().toString();

            // Prevent overwriting by appending timestamp
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString(); // Return full path to save in DB
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file: " + file.getOriginalFilename(), ex);
        }
    }
}
