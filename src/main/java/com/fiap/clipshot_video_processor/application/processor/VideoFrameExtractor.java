package com.fiap.clipshot_video_processor.application.processor;

import java.awt.image.BufferedImage;
import java.util.List;

public interface VideoFrameExtractor {
    List<BufferedImage> execute(String videoPath);
}
