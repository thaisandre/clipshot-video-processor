package com.fiap.clipshot_video_processor.infrastructure.videoTask;

import com.fiap.clipshot_video_processor.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoTaskRepository extends JpaRepository<VideoTaskEntity, Long> {

    Optional<VideoTaskEntity> findByVideoId(String videoId);

    Optional<VideoTaskEntity> findByUserIdAndVideoId(Long userId, String videoId);

    Page<VideoTaskEntity> findAllByUserIdAndStatusNotIn(Long userId, List<Status> status, Pageable pageable);

    List<VideoTaskEntity> findAllByStatusIn(List<Status> status);


    @Query(value = """
        SELECT * FROM video_task 
        WHERE status NOT IN ('PROCESSING') 
        AND created_at >= :startTime""", nativeQuery = true)
    List<VideoTaskEntity> findAllToDeleteFiles(@Param("startTime") LocalDateTime startTime);


    @Modifying
    @Query(value = """
        UPDATE video_task set status = 'COMPLETED',
                              zip_url = :zipUrl,
                              updated_at = now()
        WHERE video_id = :videoId""", nativeQuery = true)
    void updateSuccess(@Param("videoId") String videoId, @Param("zipUrl") String zipUrl);

    @Modifying
    @Query(value = """
        UPDATE video_task SET status = 'FAILED',
                              error_message = :errorMessage,
                              updated_at = now()
        WHERE video_id = :videoId""", nativeQuery = true)
    void updateFail(@Param("videoId") String videoId, @Param("errorMessage") String errorMessage);

    @Modifying
    @Query(value = """
        UPDATE video_task SET retry_count = :retryCount,
                              updated_at = now()
        WHERE video_id = :videoId""", nativeQuery = true)
    void updateRetry(@Param("videoId") String videoId, @Param("retryCount") long retryCount);

    @Modifying
    @Query(value = """
        UPDATE video_task SET status = 'EXPIRED', updated_at = now()
        WHERE created_at <= CURRENT_DATE - INTERVAL 3 DAY""", nativeQuery = true)
    void updateExpired();
}
