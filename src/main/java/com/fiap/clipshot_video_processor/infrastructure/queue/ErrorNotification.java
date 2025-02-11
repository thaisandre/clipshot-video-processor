package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.domain.VideoTask;

public record ErrorNotification(String id, Long userId) {

    public ErrorNotification(VideoTask videoTask) {
        this(videoTask.getVideoId(), videoTask.getUserId());
    }
}
