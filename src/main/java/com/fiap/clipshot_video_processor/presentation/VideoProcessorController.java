package com.fiap.clipshot_video_processor.presentation;

import com.fiap.clipshot_video_processor.application.videoTask.retrieve.FindVideoTaskByUserIdAndVideoIdUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.FindVideoTaskCommand;
import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.FindVideoTasksCommand;
import com.fiap.clipshot_video_processor.application.videoTask.retrieveAll.FindVideoTasksUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class VideoProcessorController {

    private final FindVideoTaskByUserIdAndVideoIdUseCase findVideoTaskByUserIdAndVideoIdUseCase;
    private final FindVideoTasksUseCase findVideoTasksUseCase;

    public VideoProcessorController(FindVideoTaskByUserIdAndVideoIdUseCase findVideoTaskByUserIdAndVideoIdUseCase, FindVideoTasksUseCase findVideoTasksUseCase) {
        this.findVideoTaskByUserIdAndVideoIdUseCase = findVideoTaskByUserIdAndVideoIdUseCase;
        this.findVideoTasksUseCase = findVideoTasksUseCase;
    }

    @GetMapping("/user/{userId}/video/{videoId}")
    public ResponseEntity<?> getFileByUserIdAndVideoId(
            @PathVariable Long userId,
            @PathVariable String videoId) {
        return findVideoTaskByUserIdAndVideoIdUseCase.execute(new FindVideoTaskCommand(userId, videoId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId,
                                            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(findVideoTasksUseCase.execute(
                new FindVideoTasksCommand(userId, Optional.of(page).orElse(0)))
        );
    }
}
