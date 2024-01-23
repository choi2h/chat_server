package com.ffs.chat.repository;

import com.ffs.chat.dto.ChatRoom;
import com.ffs.chat.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private Map<String, ChatRoom> chatRoomMap;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
        topics = new HashMap<>();
    }

    public List<ChatRoom> findAllRooms(){
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name){
        ChatRoom room = ChatRoom.create(name);
        chatRoomMap.put(room.getRoomId(), room);

        ChannelTopic topic = topics.get(room.getRoomId());
        if(topic == null) {
            createTopic(room.getRoomId());
        }

        return room;
    }

    private void createTopic(String roomId) {
        ChannelTopic topic = new ChannelTopic(roomId);
        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);

    }
}