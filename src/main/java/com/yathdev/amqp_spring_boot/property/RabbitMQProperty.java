package com.yathdev.amqp_spring_boot.property;

import com.yathdev.amqp_spring_boot.model.QueueData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperty {
    private List<QueueData> queues;
}
