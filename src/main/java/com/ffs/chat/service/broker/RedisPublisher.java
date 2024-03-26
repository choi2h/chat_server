package com.ffs.chat.service.broker;

import com.ffs.chat.dto.ChatMessageDto;
import com.ffs.chat.repository.TopicRepository;
import com.ffs.chat.service.ChatMessageService;
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
    private final TopicRepository topicRepository;
    private final ChatMessageService chatMessageService;

    public void publish(Long roomId, ChatMessageDto message) {
        log.info("Publish message on {}. message={}", roomId, message.getMessage());

        chatMessageService.saveChatMessage(roomId, message.getWriterId(), message.getWriterName(), message.getMessage());

        ChannelTopic topic = getTopic(roomId);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    private ChannelTopic getTopic(Long roomId) {
        return topicRepository.getTopic(roomId);
    }

}