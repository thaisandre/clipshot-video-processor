package com.fiap.clipshot_video_processor.application.processor;

import java.io.File;

public interface VideoProcessorGateway {
    File createFramesZip(String videoUrl, String fileName);
}
