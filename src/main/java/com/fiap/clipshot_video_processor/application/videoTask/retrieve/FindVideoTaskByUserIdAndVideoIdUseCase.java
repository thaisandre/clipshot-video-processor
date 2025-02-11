package com.fiap.clipshot_video_processor.application.videoTask.retrieve;

import com.fiap.clipshot_video_processor.application.commons.UseCase;

import java.util.Optional;

public abstract class FindVideoTaskByUserIdAndVideoIdUseCase extends UseCase<FindVideoTaskCommand, Optional<VideoTaskView>> {
}
