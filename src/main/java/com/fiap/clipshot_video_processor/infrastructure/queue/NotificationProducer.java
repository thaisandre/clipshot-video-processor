package com.fiap.clipshot_video_processor.infrastructure.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class NotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(ErrorNotification errorNotification) {
        rabbitTemplate.convertAndSend("notification-queue", errorNotification);
        logger.info("[NotificationProducer] Notificação de falha no vídeo {} enviada para o usuario {} com sucesso.",
                errorNotification.id(), errorNotification.userId());
    }
}
