package com.fiap.clipshot_video_processor.infrastructure.processor.ffmpeg;

import com.fiap.clipshot_video_processor.application.processor.VideoFrameExtractor;
import com.fiap.clipshot_video_processor.application.processor.VideoZipFramesCreator;
import com.fiap.clipshot_video_processor.application.processor.VideoProcessorGateway;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class FFmpegVideoProcessorGateway implements VideoProcessorGateway {

    private static final String TMP_DIR = "tmp/images";

    private final VideoFrameExtractor videoFrameExtractor;
    private final VideoZipFramesCreator videoZipFramesCreator;

    public FFmpegVideoProcessorGateway(VideoFrameExtractor videoFrameExtractor, VideoZipFramesCreator videoZipFramesCreator) {
        this.videoFrameExtractor = videoFrameExtractor;
        this.videoZipFramesCreator = videoZipFramesCreator;
    }

    @Override
    public File createFramesZip(String videoUrl, String fileName) {
        List<BufferedImage> frames =  videoFrameExtractor.execute(videoUrl);
        String imagesDir = "%s/%s".formatted(TMP_DIR, fileName);
        return videoZipFramesCreator.execute(frames, imagesDir, fileName);
    }
}
