package com.fiap.clipshot_video_processor.application.videoTask.retrieveAll;

import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.VideoTaskView;

public class DefaultFindVideoTasksUseCase extends FindVideoTasksUseCase{

    private final VideoTaskGateway videoTaskGateway;

    public DefaultFindVideoTasksUseCase(VideoTaskGateway videoTaskGateway) {
        this.videoTaskGateway = videoTaskGateway;
    }

    public PageableVideoTaskView execute(FindVideoTasksCommand command) {
        return videoTaskGateway.findAllBy(command.userId(), command.page());
    }
}
