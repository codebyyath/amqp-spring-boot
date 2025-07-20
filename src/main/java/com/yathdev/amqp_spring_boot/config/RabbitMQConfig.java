package com.yathdev.amqp_spring_boot.config;

import com.yathdev.amqp_spring_boot.model.QueueData;
import com.yathdev.amqp_spring_boot.property.RabbitMQProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private static final String DLQ_SUFFIX = ".dlq";
    private final RabbitMQProperty rabbitMQProperty;
    private final ConnectionFactory connectionFactory;

    @Bean
    public Declarables rabbitmqDeclarables() {
        final List<Declarable> declarables = new ArrayList<>();

        // Create exchanges
        rabbitMQProperty.getQueues().forEach(queueData -> {
            final String exchangeName = queueData.exchange();
            final String dlqExchangeName = queueData.exchangeDlq();

            final TopicExchange mainExchange = new TopicExchange(exchangeName);
            final TopicExchange dlqExchange = new TopicExchange(dlqExchangeName);

            declarables.add(mainExchange);
            declarables.add(dlqExchange);

            // Create queues
            final Queue queue = createQueue(queueData);
            final Queue dlqQueue = createDlqQueue(queueData);
            final Binding binding = createBinding(queue, exchangeName, queueData, false);
            final Binding dlqBinding = createBinding(dlqQueue, exchangeName, queueData, true);

            declarables.addAll(List.of(queue, dlqQueue, binding, dlqBinding));
        });

        return new Declarables(declarables);
    }

    private Queue createQueue(final QueueData queueData) {
        return QueueBuilder.durable(queueData.queue())
                .deadLetterExchange(queueData.exchangeDlq())
                .deadLetterRoutingKey(queueData.routingKey() + DLQ_SUFFIX)
                .build();
    }

    private Queue createDlqQueue(final QueueData queueData) {
        return QueueBuilder.durable(queueData.queueDlq()).build();
    }

    private Binding createBinding(final Queue queue,
                                  final String exchangeName,
                                  final QueueData queueData,
                                  final boolean isDlq) {
        final var routingKey = isDlq ? queueData.routingKey() + DLQ_SUFFIX : queueData.routingKey();
        final var exchange = new TopicExchange(isDlq ? exchangeName + DLQ_SUFFIX : exchangeName);

        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        return factory;
    }

}
