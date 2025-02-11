package com.fiap.clipshot_video_processor.infrastructure.job;

import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import com.fiap.clipshot_video_processor.domain.VideoTask;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
public class CleanerJob {

    public final VideoTaskGateway videoTaskGateway;

    public CleanerJob(VideoTaskGateway videoTaskGateway) {
        this.videoTaskGateway = videoTaskGateway;
    }

    @Scheduled(fixedRate = 10000)
    public void process() {
        videoTaskGateway.expiredTasks();
        List<VideoTask> tasksToDeleteFiles = videoTaskGateway.findAllToDeleteFiles(LocalDateTime.now().minusHours(5));

        tasksToDeleteFiles.stream().forEach(task -> {
            Path imagePath = Paths.get("%s/%s".formatted("tmp/images", task.getFileName()));
            Path zipFile = Paths.get("%s/%s".formatted("tmp/zips/", task.getFileName()));

            try {
                if(Paths.get(imagePath.toString()).toFile().exists()) {
                    deleteDirectory(imagePath);
                }
                if(Paths.get(zipFile.toString()).toFile().exists()) {
                    Files.deleteIfExists(zipFile);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteDirectory(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}