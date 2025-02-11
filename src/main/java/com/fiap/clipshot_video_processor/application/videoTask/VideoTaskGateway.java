package com.fiap.clipshot_video_processor.application.videoTask;


import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.PageableVideoTaskView;
import com.fiap.clipshot_video_processor.domain.VideoTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoTaskGateway {
    VideoTask getOrCreateVideoTask(String videoId, String videoUrl, Long userId);
    VideoTask persist(VideoTask videoTask);
    void updateSuccess(VideoTask videoTask, String zipUrl);
    void updateFail(String videoId, String errorMessage);
    void updateRetry(String videoId, long retry_count);
    PageableVideoTaskView findAllBy(Long userId, Integer page);
    Optional<VideoTask> findByUserIdAndVideoId(Long userId, String videoId);
    Optional<VideoTask> getByVideoId(String videoId);
    void expiredTasks();
    List<VideoTask> findAllToDeleteFiles(LocalDateTime startTime);
}
