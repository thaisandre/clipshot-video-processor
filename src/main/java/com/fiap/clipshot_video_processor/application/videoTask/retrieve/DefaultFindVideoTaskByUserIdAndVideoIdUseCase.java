package com.fiap.clipshot_video_processor.application.videoTask.retrieve;


import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;

import java.util.Optional;

public class DefaultFindVideoTaskByUserIdAndVideoIdUseCase extends FindVideoTaskByUserIdAndVideoIdUseCase {

    private final VideoTaskGateway videoTaskGateway;

    public DefaultFindVideoTaskByUserIdAndVideoIdUseCase(VideoTaskGateway videoTaskGateway) {
        this.videoTaskGateway = videoTaskGateway;
    }

    public Optional<VideoTaskView> execute(FindVideoTaskCommand command) {
        return videoTaskGateway.findByUserIdAndVideoId(command.userId(), command.videoId())
                .map(VideoTaskView::new);
    }
}
