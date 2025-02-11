package com.fiap.clipshot_video_processor.application.processor;

import com.fiap.clipshot_video_processor.application.storage.StorageGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DefaultVideoProcessorUseCase extends VideoProcessorUseCase {

    private static final Logger logger = LoggerFactory.getLogger(DefaultVideoProcessorUseCase.class);
    private static final String BUCKET_NAME = "clipshot-frames";

    private final VideoProcessorGateway videoProcessorGateway;
    private final StorageGateway storageGateway;

    public DefaultVideoProcessorUseCase(VideoProcessorGateway videoProcessorGateway, StorageGateway storageGateway) {
        this.videoProcessorGateway = videoProcessorGateway;
        this.storageGateway = storageGateway;
    }

    public String execute(VideoProcessCommand command) {
        try {
            File zipFile = videoProcessorGateway.createFramesZip(command.videoUrl(), command.fileName());
            String link = storageGateway.upload(zipFile, BUCKET_NAME);
            logger.info("[DefaultVideoProcessorUseCase] Video {} processed successfully: {}", command.videoUrl(), zipFile.getName());
            return link;
        } catch (Exception e) {
            logger.error("[DefaultVideoProcessorUseCase] Failed to process video {}: {}", command.videoUrl(), e.getMessage());
            throw new VideoProcessorException(e.getMessage());
        }
    }
}
