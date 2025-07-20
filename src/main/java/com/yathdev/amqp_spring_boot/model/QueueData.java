package com.yathdev.amqp_spring_boot.model;

public record QueueData (String queue, String exchange, String exchangeDlq, String queueDlq, String routingKey) {}
