package com.fiap.clipshot_video_processor.infrastructure.processor.ffmpeg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FFmpegVideoFrameExtractorTest {

    private FFmpegVideoFrameExtractor videoFrameExtractor;

    @BeforeEach
    void setUp() {
        videoFrameExtractor = new FFmpegVideoFrameExtractor();
    }

    @Test
    void testExecute_ValidVideo_ReturnsFrames() throws Exception {
        String videoPath = "src/test/resources/video_teste.mp4";
        List<BufferedImage> frames = videoFrameExtractor.execute(videoPath);
        assertThat(frames).isNotNull();
        assertThat(frames).hasSize(4);
    }

    @Test
    void testExecute_EmptyVideo_ReturnsEmptyList() throws Exception {
        List<BufferedImage> frames = videoFrameExtractor.execute("src/test/resources/one_frame_video.mp4");
        assertThat(frames).isNotNull();
        assertThat(frames).hasSize(1);
    }
}