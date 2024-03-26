package com.ffs.chat.repository;

import com.ffs.chat.service.broker.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TopicRepository {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;

    private Map<Long, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        this.topics = new HashMap<>();
    }

    public ChannelTopic getTopic(Long roomId) {
        return topics.get(roomId);
    }

    public void registerChannelTopic(Long roomId) {
        if(topics.get(roomId) != null) {
            return;
        }

        ChannelTopic topic = new ChannelTopic(String.valueOf(roomId));
        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }
}
