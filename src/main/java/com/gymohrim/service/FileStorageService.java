package com.gymohrim.service;

import com.gymohrim.exception.file.BucketCreationException;
import com.gymohrim.exception.file.FileUploadException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        createBucket();
    }


    private void createBucket() {
        try {
            String sanitizedBucketName = bucketName.trim();
            log.info("Checking if bucket '{}' exists...", sanitizedBucketName);
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(sanitizedBucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(sanitizedBucketName).build());
                log.info("Bucket '{}' created successfully.", sanitizedBucketName);
            } else {
                log.info("Bucket '{}' already exists.", sanitizedBucketName);
            }
        } catch (Exception e) {
            log.error("Error occurred while creating bucket '{}': {}", bucketName, e.getMessage(), e);
            throw new BucketCreationException("Failed to create or check bucket: " + bucketName, e);
        }
    }

    public String uploadFile(MultipartFile file, String fileName) {
        try (InputStream inputStream = file.getInputStream()) {
            log.info("Uploading file '{}' to bucket '{}'", fileName, bucketName);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("File '{}' uploaded successfully to bucket '{}'", fileName, bucketName);

            String fileUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .method(Method.GET)
                            .object(fileName)
                            .build()
            );
            log.info("Generated presigned URL for file '{}': {}", fileName, fileUrl);

            return fileUrl;
        } catch (Exception e) {
            log.error("Error uploading file '{}' to bucket '{}': {}", fileName, bucketName, e.getMessage(), e);
            throw new FileUploadException("Failed to upload file: " + fileName, e);
        }
    }


}
