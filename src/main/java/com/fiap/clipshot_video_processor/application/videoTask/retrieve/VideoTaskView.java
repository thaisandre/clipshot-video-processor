package com.fiap.clipshot_video_processor.application.videoTask.retrieve;

import com.fiap.clipshot_video_processor.domain.Status;
import com.fiap.clipshot_video_processor.domain.VideoTask;

import java.time.LocalDateTime;

public record VideoTaskView(
        String videoUrl,
        String framesUrl,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public VideoTaskView(VideoTask videoTask) {
        this(videoTask.getVideoUrl(),
                videoTask.getFramesUrl(),
                videoTask.getStatus(),
                videoTask.getCreatedAt(),
                videoTask.getUpdatedAt());
    }
}
