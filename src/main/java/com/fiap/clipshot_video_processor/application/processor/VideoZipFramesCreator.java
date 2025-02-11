package com.fiap.clipshot_video_processor.application.processor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface VideoZipFramesCreator {
    File execute(List<BufferedImage> frames, String folderPath, String fileName);
}
