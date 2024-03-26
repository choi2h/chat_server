package com.ffs.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "CREATE_AT")
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    List<RoomUser> roomUserList;

    public ChatRoom(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        roomUserList = new ArrayList<>();
    }

    public void addRoomUser(RoomUser newRoomUser) {
        for(RoomUser roomUser : roomUserList) {
            if(roomUser.getUserId().equals(newRoomUser.getUserId())) {
                return;
            }
        }

        roomUserList.add(newRoomUser);
    }
}
