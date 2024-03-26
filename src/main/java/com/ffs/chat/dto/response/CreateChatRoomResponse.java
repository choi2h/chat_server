package com.ffs.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomResponse {
    private Long roomId;
    private Long targetUserId;
    private String targetUserName;
    private Long writerId;
    private String writerName;
}
