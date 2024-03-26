package com.ffs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageDto {
    private Long roomId;
    private Long writerId;
    private String writerName;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
