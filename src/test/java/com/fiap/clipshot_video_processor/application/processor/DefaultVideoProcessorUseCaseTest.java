package com.fiap.clipshot_video_processor.application.processor;

import com.fiap.clipshot_video_processor.application.storage.StorageGateway;
import com.fiap.clipshot_video_processor.infrastructure.storage.s3.S3StorageGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DefaultVideoProcessorUseCaseTest {

    private static final String BUCKET_NAME = "clipshot-frames";
    private VideoProcessorGateway videoProcessorGateway;
    private StorageGateway storageGateway;
    private DefaultVideoProcessorUseCase processorUseCase;

    @BeforeEach
    void setUp() {
        videoProcessorGateway = mock(VideoProcessorGateway.class);
        storageGateway = mock(S3StorageGateway.class);
        processorUseCase = new DefaultVideoProcessorUseCase(videoProcessorGateway, storageGateway);
    }

    @Test
    void execute_shouldProcessVideoSuccessfully() {
        String fileName = "test_video";
        String videoUrl = "http://example.com/video.mp4";
        File zipFile = new File(fileName + ".zip");
        String zipUrl = "http://example.com/video.zip";


        VideoProcessCommand command = mock(VideoProcessCommand.class);
        when(command.videoUrl()).thenReturn(videoUrl);
        when(command.fileName()).thenReturn(fileName);

        doReturn(zipFile).when(videoProcessorGateway).createFramesZip(videoUrl, fileName);
        doReturn(zipUrl).when(storageGateway).upload(zipFile, BUCKET_NAME);

        String result = processorUseCase.execute(command);

        assertThat(result).isEqualTo(zipUrl);
        verify(videoProcessorGateway).createFramesZip(videoUrl, fileName);
        verify(storageGateway).upload(zipFile, BUCKET_NAME);
    }

    @Test
    void execute_shouldThrowException_whenVideoProcessorFails() {
        String videoUrl = "http://example.com/video.mp4";
        String fileName = "test_video";

        VideoProcessCommand command = mock(VideoProcessCommand.class);
        when(command.videoUrl()).thenReturn(videoUrl);
        when(command.fileName()).thenReturn(fileName);

        doThrow(new RuntimeException("error message"))
                .when(videoProcessorGateway).createFramesZip(videoUrl, fileName);

        assertThatThrownBy(() -> processorUseCase.execute(command))
            .isInstanceOf(RuntimeException.class)
                .hasMessage("error message");

        verify(videoProcessorGateway).createFramesZip(videoUrl, fileName);
        verify(storageGateway, never()).upload(any(File.class), anyString());
    }

    @Test
    void shouldThrowExceptionWhenUploadFails() {
        String videoUrl = "http://example.com/video.mp4";
        String fileName = "test_video";
        File zipFile = new File(fileName + ".zip");

        VideoProcessCommand command = mock(VideoProcessCommand.class);
        when(command.videoUrl()).thenReturn(videoUrl);
        when(command.fileName()).thenReturn(fileName);

        doReturn(zipFile).when(videoProcessorGateway).createFramesZip(videoUrl, fileName);
        doThrow(new RuntimeException("error message"))
                .when(storageGateway).upload(zipFile, BUCKET_NAME);


        assertThatThrownBy(() -> processorUseCase.execute(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("error message");

        verify(videoProcessorGateway).createFramesZip(videoUrl, fileName);
        verify(storageGateway).upload(zipFile, BUCKET_NAME);
    }
}