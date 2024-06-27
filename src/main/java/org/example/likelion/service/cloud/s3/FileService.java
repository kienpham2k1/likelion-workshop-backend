package org.example.likelion.service.cloud.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;
}
