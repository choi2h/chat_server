package com.ffs.chat.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification {
    private String title;
    private String body;

    @Builder
    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
