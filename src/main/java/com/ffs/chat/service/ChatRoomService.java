package com.ffs.chat.service;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> findAllRooms();

    void createChatRoom(CreateChatRoomRequest request);

}
