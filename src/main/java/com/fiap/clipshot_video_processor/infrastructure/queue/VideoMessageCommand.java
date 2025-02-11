package com.fiap.clipshot_video_processor.infrastructure.queue;

public record VideoMessageCommand(
        String id,
        String videoUrl,
        Long userId) {}
