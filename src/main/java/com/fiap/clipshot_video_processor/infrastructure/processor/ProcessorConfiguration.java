package com.fiap.clipshot_video_processor.infrastructure.processor;

import com.fiap.clipshot_video_processor.application.processor.*;
import com.fiap.clipshot_video_processor.infrastructure.processor.ffmpeg.FFmpegVideoFrameExtractor;
import com.fiap.clipshot_video_processor.infrastructure.processor.ffmpeg.FFmpegVideoProcessorGateway;
import com.fiap.clipshot_video_processor.infrastructure.storage.s3.S3StorageGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfiguration {

    @Bean
    public VideoFrameExtractor videoFrameExtractor() {
        return new FFmpegVideoFrameExtractor();
    }

    @Bean
    public VideoZipFramesCreator videoZipFramesCreator() {
        return new DefaultVideoZipFramesCreator();
    }

    @Bean
    public VideoProcessorGateway videoProcessorGateway(VideoFrameExtractor videoFrameExtractor, VideoZipFramesCreator videoZipFramesCreator) {
        return new FFmpegVideoProcessorGateway(videoFrameExtractor, videoZipFramesCreator);
    }

    @Bean
    public VideoProcessorUseCase videoProcessorUseCase(VideoProcessorGateway videoProcessorGateway, S3StorageGateway s3StorageGateway) {
        return new DefaultVideoProcessorUseCase(videoProcessorGateway, s3StorageGateway);
    }
}
