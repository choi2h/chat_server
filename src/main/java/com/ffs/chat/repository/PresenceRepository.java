package com.ffs.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PresenceRepository {


    private static final String CONNECTION_COUNT_KEY = "connection-count";
    private final RedisTemplate<String, String> redisTemplate;

    public Long increaseConnectionCount() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String connectionCountValue = valueOperations.get(CONNECTION_COUNT_KEY);
        Long connectionCount = 1L;
        if(connectionCountValue == null || connectionCountValue.isEmpty()) {
            valueOperations.set(CONNECTION_COUNT_KEY, "1");
        } else {
            connectionCount = Long.parseLong(connectionCountValue) + 1;
            valueOperations.set(CONNECTION_COUNT_KEY, String.valueOf(connectionCount));
        }

        return connectionCount;
    }

    public Long decreaseConnectionCount() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String connectionCountValue = valueOperations.get(CONNECTION_COUNT_KEY);
        Long connectionCount = 0L;
        if(connectionCountValue == null || connectionCountValue.isEmpty()) {
            valueOperations.set(CONNECTION_COUNT_KEY, "0");
        } else {
            connectionCount = Long.parseLong(connectionCountValue);
            connectionCount = connectionCount.equals(0L)? 0 : connectionCount-1;
            valueOperations.set(CONNECTION_COUNT_KEY, String.valueOf(connectionCount));
        }

        return connectionCount;

    }

    public void saveUserPresence(String userKey, String roomId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(userKey, roomId);
    }

    public String getUserPresence(String userKey) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(userKey);
    }

    public void saveSessionInfo(String sessionKey, String userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(sessionKey, userId);
    }

    public String getUserId(String sessionKey) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(sessionKey);
    }
}
