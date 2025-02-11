package com.fiap.clipshot_video_processor.infrastructure.storage.s3;

import com.fiap.clipshot_video_processor.application.storage.StorageGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3StorageGateway implements StorageGateway {

    private static final Logger logger = LoggerFactory.getLogger(S3StorageGateway.class);
    private final S3Client s3Client;

    public S3StorageGateway(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String upload(File file, String folder) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(folder)
                .key(file.getName())
                .build();

        try {
            s3Client.putObject(putObjectRequest, file.toPath());
            logger.info("[S3StorageGateway] File uploaded to S3: {}", file.getName());
            return get(file.getName(), folder);
        } catch (SdkException e) {
            logger.error("[S3StorageGateway] Failed to upload file {} to S3", file.getName());
            throw new RuntimeException(e);
        }
    }

    private String get(String key, String folder) {
        GetUrlRequest request = GetUrlRequest.builder().bucket(folder).key(key).build();
        return s3Client.utilities().getUrl(request).toExternalForm();
    }
}
