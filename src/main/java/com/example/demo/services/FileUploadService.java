package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File destinationFile = new File(uploadDirectory + "/" + fileName);
        file.transferTo(destinationFile);
        return fileName;
    }
}
