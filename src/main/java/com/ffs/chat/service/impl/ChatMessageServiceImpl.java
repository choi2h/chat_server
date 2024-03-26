package com.ffs.chat.service.impl;

import com.ffs.chat.model.ChatMessage;
import com.ffs.chat.repository.ChatMessageRepository;
import com.ffs.chat.service.ChatMessageService;
import com.ffs.chat.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private static final String ID_PREFIX = "chat";
    private static final String ID_TYPE = "msg";

    private final ChatMessageRepository chatMessageRepository;
    private final IdGenerator idGenerator;

    @Override
    public void saveChatMessage(Long roomId, Long userId, String userName, String message) {
        String messageId = idGenerator.createNewId(ID_PREFIX, ID_TYPE);

        ChatMessage chatMessage = ChatMessage.builder()
                .messageId(messageId)
                .writerId(userId)
                .writerName(userName)
                .roomId(roomId)
                .content(message)
                .sendTime(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessage);
    }
}
