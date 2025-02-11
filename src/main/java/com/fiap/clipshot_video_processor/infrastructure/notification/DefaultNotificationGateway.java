package com.fiap.clipshot_video_processor.infrastructure.notification;

import com.fiap.clipshot_video_processor.application.notification.NotificationGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import com.fiap.clipshot_video_processor.infrastructure.queue.ErrorNotification;
import com.fiap.clipshot_video_processor.infrastructure.queue.NotificationProducer;

public class DefaultNotificationGateway implements NotificationGateway {

    private final NotificationProducer notificationProducer;

    public DefaultNotificationGateway(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    @Override
    public void send(VideoTask videoTask) {
        notificationProducer.publish(new ErrorNotification(videoTask));
    }
}
