package com.yathdev.amqp_spring_boot.producer;

import com.yathdev.amqp_spring_boot.model.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queues[0].exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.queues[0].routingKey}")
    private String routingKey;
    @Value("${spring.rabbitmq.queues[0].queue}")
    private String queue;

    public void sendMessage(final MessageDto message) {
        log.info("Publish Message to Queue[{}] successfully with message: {}", queue, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
