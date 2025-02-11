package com.fiap.clipshot_video_processor.infrastructure.videoTask;

import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.DefaultFindVideoTaskByUserIdAndVideoIdUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.FindVideoTaskByUserIdAndVideoIdUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.DefaultFindVideoTasksUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.FindVideoTasksUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoTaskGatewayConfiguration {

    @Bean
    public VideoTaskGateway videoTaskGateway(VideoTaskRepository videoTaskRepository) {
        return new DefaultVideoTaskGateway(videoTaskRepository);
    }

    @Bean
    public FindVideoTaskByUserIdAndVideoIdUseCase findVideoTaskByUserIdAndVideoId(VideoTaskGateway videoTaskGateway) {
        return new DefaultFindVideoTaskByUserIdAndVideoIdUseCase(videoTaskGateway);
    }

    @Bean
    public FindVideoTasksUseCase findVideoTasksUseCase(VideoTaskGateway videoTaskGateway) {
        return new DefaultFindVideoTasksUseCase(videoTaskGateway);
    }

}
