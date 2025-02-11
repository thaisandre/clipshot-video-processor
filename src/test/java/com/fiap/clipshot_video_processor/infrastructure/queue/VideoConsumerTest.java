package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.application.processor.VideoProcessCommand;
import com.fiap.clipshot_video_processor.application.processor.VideoProcessorUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import org.flywaydb.core.internal.util.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.Mockito.*;

class VideoConsumerTest {

    private VideoTaskGateway videoTaskGateway;
    private VideoProcessorUseCase videoProcessorUseCase;
    private VideoConsumer videoConsumer;

    @BeforeEach
    void setUp() {
        videoTaskGateway = mock(VideoTaskGateway.class);
        videoProcessorUseCase = mock(VideoProcessorUseCase.class);
        videoConsumer = new VideoConsumer(videoTaskGateway, videoProcessorUseCase);
    }

    @Test
    void onMessage_shouldConsumeMessageAndProcessVideoSuccessfully() {
        String id = UUID.randomUUID().toString();
        String videoUrl = "http://example.com/video.mp4";
        String zipPath = "processed/video_123.zip";

        VideoMessageCommand command = new VideoMessageCommand(id, videoUrl, 1L);
        Message message = mock(Message.class);
        when(message.getBody()).thenReturn(JsonUtils.toJson(command).getBytes(StandardCharsets.UTF_8));

        VideoTask videoTask = new VideoTask(id, videoUrl, 1L);
        when(videoTaskGateway.getOrCreateVideoTask(id, videoUrl, 1L)).thenReturn(videoTask);
        when(videoProcessorUseCase.execute(any(VideoProcessCommand.class))).thenReturn(zipPath);

        videoConsumer.onMessage(message);
        verify(videoTaskGateway).updateSuccess(videoTask, zipPath);
    }

}