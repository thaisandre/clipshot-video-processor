package com.fiap.clipshot_video_processor.infrastructure.videoTask;

import com.fiap.clipshot_video_processor.domain.Status;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Table(name = "video_task")
public class VideoTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt = now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "video_url")
    private String videoUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PROCESSING;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "frames_url")
    private String framesUrl;

    @Column(name = "retry_count")
    private int retryCount;

    @Column(name = "error_message")
    private String errorMessage;

    @Deprecated
    public VideoTaskEntity() {}

    public VideoTaskEntity(VideoTask videoTask) {
        this.id = videoTask.getId();
        this.createdAt = videoTask.getCreatedAt();
        this.updatedAt = videoTask.getUpdatedAt();
        this.videoId = videoTask.getVideoId();
        this.videoUrl = videoTask.getVideoUrl();
        this.status = videoTask.getStatus();
        this.userId = videoTask.getUserId();
        this.framesUrl = videoTask.getFramesUrl();
        this.retryCount = videoTask.getRetryCount();
        this.errorMessage = videoTask.getErrorMessage();
    }

    public VideoTask toDomain() {
        return new VideoTask(id, createdAt, updatedAt, videoId, videoUrl, status, userId, framesUrl, retryCount, errorMessage);
    }
}
