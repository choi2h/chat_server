package com.ffs.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ChatRoomDto {

    private Long roomId;
    private String roomName;
    private List<UserInfo> userInfoList;
    private LocalDateTime createAt;

    @Builder
    public ChatRoomDto(Long roomId, LocalDateTime createAt){
        this.roomId = roomId;
        this.createAt = createAt;
        this.userInfoList = new ArrayList<>();
    }

    public void addUserInfo(Long userId, String userName) {
        UserInfo userInfo = new UserInfo(userId, userName);
        userInfoList.add(userInfo);
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}

class UserInfo {
    Long userId;
    String userName;

    UserInfo(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}