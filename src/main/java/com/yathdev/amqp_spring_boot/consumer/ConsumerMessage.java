package com.yathdev.amqp_spring_boot.consumer;

import com.yathdev.amqp_spring_boot.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerMessage {

    @RabbitListener(queues = {"${spring.rabbitmq.queues[0].queue}"}, concurrency = "10")
    public void consumeJson(final MessageDto message) {
        log.info("Received Queue[{}] Message: {}", "${spring.rabbitmq.queues[0].queue}",  message);
    }

}