package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.application.processor.VideoProcessCommand;
import com.fiap.clipshot_video_processor.application.processor.VideoProcessorUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import com.fiap.clipshot_video_processor.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class VideoConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(VideoConsumer.class);

    private final VideoTaskGateway videoTaskGateway;
    private final VideoProcessorUseCase videoProcessorUseCase;

    public VideoConsumer(VideoTaskGateway videoTaskGateway, VideoProcessorUseCase videoProcessorUseCase) {
        this.videoTaskGateway = videoTaskGateway;
        this.videoProcessorUseCase = videoProcessorUseCase;
    }

    @Override
    public void onMessage(Message message) {
        VideoMessageCommand body = JsonUtils.fromJson(message.getBody(), VideoMessageCommand.class);
        logger.info("[VideoConsumer] Consumindo mensagem: {}", body);

        VideoTask videoTask = videoTaskGateway.getOrCreateVideoTask(
                body.id(), body.videoUrl(), body.userId());

        String zipPath = videoProcessorUseCase.execute(new VideoProcessCommand(videoTask));
        videoTaskGateway.updateSuccess(videoTask, zipPath);
    }
}
