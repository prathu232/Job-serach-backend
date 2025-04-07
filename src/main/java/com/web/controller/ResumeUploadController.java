package com.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/upload")
public class ResumeUploadController {

    @Value("${resume.upload.dir}") // Get path from application.properties
    private String uploadDir;

    @PostMapping("/resume")
    public String uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file to local folder
            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            return "Resume uploaded successfully: " + filePath; // Return file path
        } catch (IOException e) {
            return "Error uploading file: " + e.getMessage();
        }
    }
}
