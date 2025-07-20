package com.yathdev.amqp_spring_boot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MessageDto implements Serializable {
    private Long id;
    private String content;
}
