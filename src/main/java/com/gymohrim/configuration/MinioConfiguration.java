package com.gymohrim.configuration;


import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.client.endpoint}")
    private String minioUrl;

    @Value("${minio.client.user}")
    private String accessKey;

    @Value("${minio.client.password}")
    private String secretKey;



    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }
}
