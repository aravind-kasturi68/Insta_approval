package com.instaapproval.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileStorageService {
    private final Path basePath;
    public FileStorageService(@Value("${app.files.base-path}") String basePath) throws IOException {
        this.basePath = Paths.get(basePath).toAbsolutePath().normalize();
        Files.createDirectories(this.basePath);
    }

    public String save(Long applicationId, String docType, MultipartFile file) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Path appDir = basePath.resolve("app-" + applicationId);
        Files.createDirectories(appDir);
        String cleanName = docType + "-" + timestamp + "-" + file.getOriginalFilename();
        Path target = appDir.resolve(cleanName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }
}
