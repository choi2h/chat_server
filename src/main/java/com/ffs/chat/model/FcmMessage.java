package com.ffs.chat.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    public FcmMessage(Message message) {
        validateOnly = false;
        this.message = message;
    }
}
