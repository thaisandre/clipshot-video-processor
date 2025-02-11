package com.fiap.clipshot_video_processor.application.notification;

import com.fiap.clipshot_video_processor.domain.VideoTask;

public interface NotificationGateway {
    void send(VideoTask videoTask);
}
