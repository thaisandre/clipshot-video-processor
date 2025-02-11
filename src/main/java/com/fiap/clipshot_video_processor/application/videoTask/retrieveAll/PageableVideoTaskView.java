package com.fiap.clipshot_video_processor.application.videoTask.retrieveAll;

import com.fiap.clipshot_video_processor.application.videoTask.retrieve.VideoTaskView;

import java.util.List;

public record PageableVideoTaskView(
        List<VideoTaskView> videos,
        long totalElements,
        int totalPages,
        int pageNumber,
        int pageSize
) {}
