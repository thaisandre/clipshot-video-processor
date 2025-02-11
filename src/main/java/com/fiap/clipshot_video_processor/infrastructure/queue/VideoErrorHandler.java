package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.application.notification.NotificationGateway;
import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import com.fiap.clipshot_video_processor.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

import java.util.Map;

public class VideoErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(VideoErrorHandler.class);
    private static final int MAX_RETRIES = 3;

    private final VideoTaskGateway videoTaskGateway;
    private final NotificationGateway notificationGateway;

    public VideoErrorHandler(VideoTaskGateway videoTaskGateway, NotificationGateway notificationGateway) {
        this.videoTaskGateway = videoTaskGateway;
        this.notificationGateway = notificationGateway;
    }

    @Override
    public void handleError(Throwable t) {
        ListenerExecutionFailedException exception = (ListenerExecutionFailedException) t;

        long deliveryCount = getDeliveryCount(exception);
        VideoMessageCommand message = getVideoMessage(exception);

        VideoTask task = videoTaskGateway.getOrCreateVideoTask(
                message.id(), message.videoUrl(), message.userId());

        if(deliveryCount > 0 && deliveryCount < MAX_RETRIES) {
            videoTaskGateway.updateRetry(message.id(), deliveryCount);
            logger.info("[VideoErrorHandler] Retentando processar video {}, tentativa: {}", message.id(), deliveryCount);
        } else {
            videoTaskGateway.updateFail(message.id(), exception.getMessage());
            logger.info("[VideoErrorHandler] Falha ao processar o vídeo {} após 3 tentativas", message.id());
            notificationGateway.send(task);
        }
    }

    public Long getDeliveryCount(ListenerExecutionFailedException exception) {
        Map<String, Object> headers = exception.getFailedMessage().getMessageProperties().getHeaders();
        Object deliveryCount = headers.get("x-delivery-count");
        if (deliveryCount instanceof Long count) {
            return count;
        } else {
            return 0L;
        }
    }

    public VideoMessageCommand getVideoMessage(ListenerExecutionFailedException exception) {
        byte[] body = exception.getFailedMessage().getBody();
        return JsonUtils.fromJson(body, VideoMessageCommand.class);
    }
}
