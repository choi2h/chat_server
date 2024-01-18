package com.ffs.chat.repository;

import com.ffs.chat.dto.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomDTOMap;

    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRooms(){
        return new ArrayList<>(chatRoomDTOMap.values());
    }

    public ChatRoom findRoomById(String id){
        return chatRoomDTOMap.get(id);
    }

    public ChatRoom createChatRoomDTO(String name){
        ChatRoom room = ChatRoom.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }
}