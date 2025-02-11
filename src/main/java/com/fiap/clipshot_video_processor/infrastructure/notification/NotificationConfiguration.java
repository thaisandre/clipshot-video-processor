package com.fiap.clipshot_video_processor.infrastructure.notification;

import com.fiap.clipshot_video_processor.application.notification.NotificationGateway;
import com.fiap.clipshot_video_processor.infrastructure.queue.NotificationProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationGateway notificationGateway(NotificationProducer notificationProducer) {
        return new DefaultNotificationGateway(notificationProducer);
    }
}
