package com.fiap.clipshot_video_processor.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.fiap.clipshot_video_processor.domain.Status.PROCESSING;
import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VideoTaskTest {

    @ParameterizedTest
    @NullAndEmptySource
    void constructor__shouldThrowIllegalArgumentExceptionWhenVideoIdIsNullOrEmpty(String videoId) {
        assertThatThrownBy(() -> new VideoTask(videoId, "http://teste.com", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoId cannot be null or empty");

        assertThatThrownBy(() -> new VideoTask(1L, null, now(), videoId, "http://teste.com", PROCESSING, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoId cannot be null or empty");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void constructor__shouldThrowIllegalArgumentExceptionWhenVideoUrlIsNullOrEmpty(String videoUrl) {
        assertThatThrownBy(() -> new VideoTask("content", videoUrl, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoUrl cannot be null or empty");

        assertThatThrownBy(() -> new VideoTask(1L, null, now(), "content", videoUrl, PROCESSING, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoUrl cannot be null or empty");
    }

    @Test
    void constructor__shouldThrowIllegalArgumentExceptionWhenVideoUrlIsNotValidUrl() {
        assertThatThrownBy(() -> new VideoTask("content", "content", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoUrl is not a valid URL");

        assertThatThrownBy(() -> new VideoTask(1L, null, now(), "content", "content", PROCESSING, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("videoUrl is not a valid URL");
    }

    @Test
    void constructor__shouldThrowIllegalArgumentExceptionWhenUserIdIsNull() {
        assertThatThrownBy(() -> new VideoTask("content", "http://teste.com", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("userId cannot be null");

        assertThatThrownBy(() -> new VideoTask(1L, null, now(), "content", "http://teste.com", PROCESSING, null,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("userId cannot be null");
    }

    @Test
    void constructor__shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new VideoTask(null, now(), now(), "content", "http://teste.com", PROCESSING, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id cannot be null");
    }

    @Test
    void constructor__shouldThrowIllegalArgumentExceptionWhenCreatedAtIsNull() {
        assertThatThrownBy(() -> new VideoTask(1L, null, now(), "content", "http://teste.com", PROCESSING, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("createdAt cannot be null");
    }

    @Test
    void constructor__shouldThrowIllegalArgumentExceptionWhenStatusIsNull() {
        assertThatThrownBy(() -> new VideoTask(1L, null, now(), "content", "http://teste.com", null, 1L,
                "content", 1, "content"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("createdAt cannot be null");
    }
}