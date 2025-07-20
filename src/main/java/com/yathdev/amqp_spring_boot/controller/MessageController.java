package com.yathdev.amqp_spring_boot.controller;

import com.yathdev.amqp_spring_boot.model.MessageDto;
import com.yathdev.amqp_spring_boot.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProducer messageProducer;

    @PostMapping
    public String sendMessage(@RequestBody MessageDto message) {
        messageProducer.sendMessage(message);
        return "Message sent with ID: " + message.getId();
    }
}
