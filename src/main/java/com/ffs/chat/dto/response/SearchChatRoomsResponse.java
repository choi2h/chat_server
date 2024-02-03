package com.ffs.chat.dto.response;

import com.ffs.chat.dto.ChatRoomDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchChatRoomsResponse {

    private List<ChatRoomDto> chatRooms;
}
