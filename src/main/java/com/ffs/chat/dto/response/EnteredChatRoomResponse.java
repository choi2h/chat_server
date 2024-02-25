package com.ffs.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnteredChatRoomResponse {
    private String roomId;
    private String memberName;
}
