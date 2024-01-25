package com.ffs.chat.service;

public interface ChatMessageService {

    void saveChatMessage(String memberId, String roomId, String message);
}
