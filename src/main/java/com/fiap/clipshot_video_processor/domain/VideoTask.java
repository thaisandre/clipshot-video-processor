package com.fiap.clipshot_video_processor.domain;

import com.fiap.clipshot_video_processor.utils.ValidationUtils;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class VideoTask {

    private Long id;
    private LocalDateTime createdAt = now();
    private LocalDateTime updatedAt;
    private String videoId;
    private String videoUrl;
    private Status status = Status.PROCESSING;
    private Long userId;
    private String framesUrl;
    private int retryCount;
    private String errorMessage;

    public VideoTask(String videoId, String videoUrl, Long userId) {
        ValidationUtils.notBlank(videoId, "videoId cannot be null or empty");
        ValidationUtils.notBlank(videoUrl, "videoUrl cannot be null or empty");
        ValidationUtils.isURL(videoUrl, "videoUrl is not a valid URL");
        ValidationUtils.notNull(userId, "userId cannot be null");
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.userId = userId;
    }

    public VideoTask(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String videoId, String videoUrl,
                     Status status, Long userId, String framesUrl, int retryCount, String errorMessage) {
        this(videoId, videoUrl, userId);
        ValidationUtils.notNull(id, "id cannot be null");
        ValidationUtils.notNull(createdAt, "createdAt cannot be null");
        ValidationUtils.notNull(status, "status cannot be null");
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.framesUrl = framesUrl;
        this.retryCount = retryCount;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public String getVideoId() {
        return videoId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Status getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFramesUrl() {
        return framesUrl;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void updateSuccess(String framesUrl) {
        this.framesUrl = framesUrl;
        this.status = Status.COMPLETED;
        this.updatedAt = now();
    }

    public void updateRetry() {
        this.retryCount = this.retryCount + 1;
        this.updatedAt = now();
    }

    public void updateFail(String errorMessage) {
        this.status = Status.FAILED;
        this.errorMessage = errorMessage;
        this.retryCount = this.retryCount + 1;
        this.updatedAt = now();
    }

    public String getFileName() {
        return "%s_%s".formatted(videoId, userId);
    }
}
