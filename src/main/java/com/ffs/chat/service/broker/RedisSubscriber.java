package com.ffs.chat.service.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /*
        Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메세지를 받아 처리
     */

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Send message for subscribers.");

        String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        // chatMessage 객체로 매핑
        try {
            ChatMessageDto roomMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);

            log.info("Send message on {}. {}:{}",roomMessage.getRoomId(), roomMessage.getWriterId(), roomMessage.getMessage());
            //webSocket 구독자에게 채팅 메시지 Send
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e){
            log.error("Can not parsing json. value={}", publishMessage, e);
        }

    }
}
