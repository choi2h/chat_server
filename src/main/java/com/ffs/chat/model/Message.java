package com.ffs.chat.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Message {
    private Notification notification;
    private String token;

    @Builder
    public Message(Notification notification, String token) {
        this.notification = notification;
        this.token = token;
    }
}
