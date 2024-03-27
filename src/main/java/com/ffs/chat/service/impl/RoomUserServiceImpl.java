package com.ffs.chat.service.impl;

import com.ffs.chat.model.RoomUser;
import com.ffs.chat.repository.RoomUserRepository;
import com.ffs.chat.service.RoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomUserServiceImpl implements RoomUserService {

    private final RoomUserRepository roomUserRepository;

    @Override
    public void saveRoomUser(Long roomId, Long userId, String userName) {
        RoomUser roomUSer =  RoomUser.builder()
                .roomId(roomId)
                .userId(userId)
                .userName(userName)
                .build();

        roomUserRepository.save(roomUSer);
    }

    @Override
    public List<RoomUser> findRoomUserByRoomId(Long roomId) {
        return roomUserRepository.findAllByRoomId(roomId);
    }

    @Override
    public Long findRoomIdByRoomUsers(Long userId1, Long userId2) {
        return roomUserRepository.findRoomIdByRoomUsers(userId1, userId2);
    }
}
