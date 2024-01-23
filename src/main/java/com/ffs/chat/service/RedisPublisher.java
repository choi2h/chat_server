package com.ffs.chat.service;

import com.ffs.chat.dto.ChatMessage;
import com.ffs.chat.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    public void publish(String roomId, ChatMessage message) {
        log.info("Publish message on {}. message={}", roomId, message.getMessage());
        ChannelTopic topic = getTopic(roomId);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    private ChannelTopic getTopic(String roomId) {
        return chatRoomRepository.getTopic(roomId);
    }

}