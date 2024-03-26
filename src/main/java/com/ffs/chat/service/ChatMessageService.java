package com.ffs.chat.service;

public interface ChatMessageService {

    void saveChatMessage(Long roomId, Long writerId, String writerName, String message);
}
