package com.fiap.clipshot_video_processor.infrastructure.videoTask;

import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.VideoTaskView;
import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.PageableVideoTaskView;
import com.fiap.clipshot_video_processor.domain.Status;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DefaultVideoTaskGateway implements VideoTaskGateway {

    private final VideoTaskRepository videoTaskRepository;

    public DefaultVideoTaskGateway(VideoTaskRepository videoTaskRepository) {
        this.videoTaskRepository = videoTaskRepository;
    }

    @Override
    public Optional<VideoTask> getByVideoId(String videoId) {
        return videoTaskRepository.findByVideoId(videoId)
                .map(VideoTaskEntity::toDomain);
    }

    @Override
    @Transactional
    public VideoTask persist(VideoTask videoTask) {
        return videoTaskRepository.save(new VideoTaskEntity(videoTask)).toDomain();
    }

    @Override
    public VideoTask getOrCreateVideoTask(String videoId, String videoUrl, Long userId) {
        return getByVideoId(videoId)
                .orElseGet(() -> persist(new VideoTask(videoId, videoUrl, userId)));
    }

    @Transactional
    @Override
    public void updateSuccess(VideoTask videoTask, String zipUrl) {
        videoTask.updateSuccess(zipUrl);
        persist(videoTask);
    }

    @Transactional
    @Override
    public void updateFail(String videoId, String errorMessage) {
        videoTaskRepository.updateFail(videoId, errorMessage);
        videoTaskRepository.flush();
    }

    @Transactional
    @Override
    public void updateRetry(String videoId, long retryCount) {
        videoTaskRepository.updateRetry(videoId, retryCount);
        videoTaskRepository.flush();
    }

    @Override
    public PageableVideoTaskView findAllBy(Long userId, Integer page) {
        Page<VideoTaskView> videos = videoTaskRepository.findAllByUserIdAndStatusNotIn(
                userId, List.of(Status.EXPIRED), getDefaultPageable(page))
                .map(VideoTaskEntity::toDomain).map(VideoTaskView::new);
        return new PageableVideoTaskView(videos.getContent(), videos.getTotalElements(), videos.getTotalPages(),
                videos.getPageable().getPageNumber(), videos.getPageable().getPageSize());
    }

    @Override
    public Optional<VideoTask> findByUserIdAndVideoId(Long userId, String videoId) {
        return videoTaskRepository.findByUserIdAndVideoId(userId, videoId)
                .map(VideoTaskEntity::toDomain);
    }

    @Transactional
    @Override
    public void expiredTasks() {
        videoTaskRepository.updateExpired();
    }

    @Override
    public List<VideoTask> findAllToDeleteFiles(LocalDateTime startTime) {
        return videoTaskRepository.findAllToDeleteFiles(startTime)
                .stream().map(VideoTaskEntity::toDomain).toList();
    }

    private Pageable getDefaultPageable(Integer page) {
        return Pageable.ofSize(20).withPage(Optional.of(page).orElse(0));
    }
}
