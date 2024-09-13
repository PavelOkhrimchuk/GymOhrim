package com.gymohrim.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final MinioClient minioClient;

    private static final String BUCKET_NAME = "user-profile-pictures";


    public String uploadFile(MultipartFile file, String fileName) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(BUCKET_NAME)
                            .method(Method.GET)
                            .object(fileName)
                            .build()
            );
        }
    }



}
