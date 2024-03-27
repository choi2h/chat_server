package com.ffs.chat.service;

import com.ffs.chat.model.RoomUser;

import java.util.List;

public interface RoomUserService {

    void saveRoomUser(Long roomId, Long userId, String userName);

    List<RoomUser> findRoomUserByRoomId(Long roomId);

    Long findRoomIdByRoomUsers(Long userId1, Long userId2);

}
