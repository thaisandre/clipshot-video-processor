package com.fiap.clipshot_video_processor.application.processor;

import com.fiap.clipshot_video_processor.domain.VideoTask;

public record VideoProcessCommand(String fileName, String videoUrl) {

    public VideoProcessCommand(VideoTask videoTask) {
        this(videoTask.getFileName(), videoTask.getVideoUrl());
    }
}
