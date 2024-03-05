package com.ffs.chat.service.impl;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.repository.ChatRoomRepository;
import com.ffs.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository repository;

    public List<ChatRoomDto> findAllRooms() {
        List<ChatRoomDto> rooms = repository.findAllRooms();

        log.info("Find all rooms count : {}", rooms.size());
        return rooms;
    }

    @Override
    public void createChatRoom(CreateChatRoomRequest request) {
        String roomName = request.getName();

        log.info("Create chat room : {}", roomName);
        repository.createChatRoom(roomName);
    }
}
