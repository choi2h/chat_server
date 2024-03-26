package com.ffs.chat.service;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.dto.response.CreateChatRoomResponse;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> findAllRooms(Long userId);

    CreateChatRoomResponse createChatRoom(CreateChatRoomRequest request);

}
