package com.ffs.chat.repository;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.service.broker.RedisSubscriber;
import com.ffs.chat.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomDtoRepository {

    private static final String ID_PREFIX="chat";
    private static final String ID_TYPE = "room";

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final IdGenerator idGenerator;
    private Map<String, ChatRoomDto> chatRoomMap;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
        topics = new HashMap<>();
    }

    public List<ChatRoomDto> findAllRooms(){
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoomDto findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public void createChatRoom(CreateChatRoomRequest request){
        ChatRoomDto room = getNewChatRoom(request.getName());
        chatRoomMap.put(room.getRoomId(), room);

        ChannelTopic topic = topics.get(room.getRoomId());
        if(topic == null) {
            createTopic(room.getRoomId());
        }

    }

    private ChatRoomDto getNewChatRoom(String name) {
        String roomId = idGenerator.createNewId(ID_PREFIX, ID_TYPE);
        return ChatRoomDto.builder().roomId(roomId).name(name).build();
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