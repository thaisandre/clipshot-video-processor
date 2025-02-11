package com.fiap.clipshot_video_processor.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfiguration {

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // número de threads iniciais
        executor.setMaxPoolSize(10); // número máximo de threads
        executor.setQueueCapacity(100); // capacidade da fila de espera
        executor.setThreadNamePrefix("video-thread-");
        executor.initialize();
        return executor;
    }
}
