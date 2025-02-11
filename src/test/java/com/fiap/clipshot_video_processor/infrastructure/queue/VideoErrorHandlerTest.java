package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.application.notification.NotificationGateway;
import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import org.flywaydb.core.internal.util.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

class VideoErrorHandlerTest {

    private VideoTaskGateway videoTaskGateway;
    private NotificationGateway notificationGateway;
    private VideoErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        videoTaskGateway = mock(VideoTaskGateway.class);
        notificationGateway = mock(NotificationGateway.class);
        errorHandler = new VideoErrorHandler(videoTaskGateway, notificationGateway);
    }

    @Test
    void handleError_shouldRetryProcessing_whenUnderMaxRetries() {
        String id = UUID.randomUUID().toString();
        String videoUrl = "http://example.com/video.mp4";

        VideoMessageCommand command = new VideoMessageCommand(id, videoUrl, 1L);
        VideoTask videoTask = new VideoTask(id, videoUrl, 1L);

        Map<String, Object> headers = Map.of("x-delivery-count", 2L);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(messageProperties.getHeaders()).thenReturn(headers);

        Message message = mock(Message.class);
        when(message.getBody()).thenReturn(JsonUtils.toJson(command).getBytes(StandardCharsets.UTF_8));
        when(message.getMessageProperties()).thenReturn(messageProperties);

        ListenerExecutionFailedException exception = mock(ListenerExecutionFailedException.class);
        when(exception.getFailedMessage()).thenReturn(message);

        when(videoTaskGateway.getOrCreateVideoTask(id, videoUrl, 1L))
                .thenReturn(videoTask);

        errorHandler.handleError(exception);

        verify(videoTaskGateway).updateRetry(id, 2L);
        verify(videoTaskGateway, never()).updateFail(anyString(), anyString());
        verify(notificationGateway, never()).send(any(VideoTask.class));
    }

    @Test
    void handleError_shouldMarkFailureAndSendNotification_whenMaxRetries() {
        String id = UUID.randomUUID().toString();
        String videoId = UUID.randomUUID().toString();
        String videoUrl = "http://example.com/video.mp4";

        VideoMessageCommand command = new VideoMessageCommand(id, videoUrl, 1L);
        VideoTask videoTask = new VideoTask(videoId, videoUrl, 1L);

        Map<String, Object> headers = Map.of("x-delivery-count", 3L);
        MessageProperties messageProperties = mock(MessageProperties.class);
        when(messageProperties.getHeaders()).thenReturn(headers);

        Message message = mock(Message.class);
        when(message.getBody()).thenReturn(JsonUtils.toJson(command).getBytes(StandardCharsets.UTF_8));
        when(message.getMessageProperties()).thenReturn(messageProperties);

        ListenerExecutionFailedException exception = mock(ListenerExecutionFailedException.class);
        when(exception.getFailedMessage()).thenReturn(message);

        when(videoTaskGateway.getOrCreateVideoTask(videoId, videoUrl, 1L))
                .thenReturn(videoTask);

        errorHandler.handleError(exception);

        verify(videoTaskGateway).updateFail(any(), any());
        verify(videoTaskGateway, never()).updateRetry(anyString(), anyLong());
        verify(notificationGateway).send(any());
    }
}