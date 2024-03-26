package com.ffs.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ROOM_USER")
public class RoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "LAST_CONNECTED_AT")
    private LocalDateTime lastConnectedAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Builder
    public RoomUser(Long roomId, Long userId, String userName) {
        this.roomId = roomId;
        this.userId = userId;
        this.userName = userName;

        LocalDateTime now = LocalDateTime.now();
        this.lastConnectedAt = now;
        this.createdAt = now;
    }

}
