package com.ffs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String message;

    public void setMessage(String message) {
        this.message = message;

    }
}
