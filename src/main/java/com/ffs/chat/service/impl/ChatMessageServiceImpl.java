package com.ffs.chat.service.impl;

import com.ffs.chat.model.ChatMessage;
import com.ffs.chat.repository.ChatMessageRepository;
import com.ffs.chat.service.ChatMessageService;
import com.ffs.chat.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private static final String ID_PREFIX = "chat";
    private static final String ID_TYPE = "msg";

    private final ChatMessageRepository chatMessageRepository;
    private final IdGenerator idGenerator;

    @Override
    public void saveChatMessage(String memberId, String roomId, String message) {
        String messageId = idGenerator.createNewId(ID_PREFIX, ID_TYPE);

        ChatMessage chatMessage = ChatMessage.builder()
                .messageId(messageId)
                .memberId(memberId)
                .roomId(roomId)
                .content(message)
                .build();

        chatMessageRepository.save(chatMessage);
    }
}
