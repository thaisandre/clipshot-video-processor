package com.fiap.clipshot_video_processor.infrastructure.queue;

import com.fiap.clipshot_video_processor.application.notification.NotificationGateway;
import com.fiap.clipshot_video_processor.application.processor.VideoProcessorUseCase;
import com.fiap.clipshot_video_processor.application.videoTask.VideoTaskGateway;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class QueueConfiguration {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageListenerAdapter messageListener(VideoConsumer videoConsumer, MessageConverter messageConverter) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(videoConsumer);
        messageListenerAdapter.setMessageConverter(messageConverter);
        return messageListenerAdapter;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                             MessageListenerAdapter messageListenerAdapter,
                                                             VideoTaskGateway videoTaskGateway,
                                                             NotificationGateway notificationGateway) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListenerAdapter);
        container.setQueueNames("clipshot-video-queue");
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(10);
        container.setErrorHandler(new VideoErrorHandler(videoTaskGateway, notificationGateway));
        return container;
    }

    @Bean
    public VideoConsumer videoConsumer(VideoTaskGateway videoTaskGateway, VideoProcessorUseCase videoProcessorUseCase) {
        return new VideoConsumer(videoTaskGateway, videoProcessorUseCase);
    }

    @Bean
    public NotificationProducer notificationProducer(RabbitTemplate rabbitTemplate) {
        return new NotificationProducer(rabbitTemplate);
    }
}
