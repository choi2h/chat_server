package com.ffs.chat.repository;

import com.ffs.chat.model.RoomUser;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RoomUserRepository extends Repository<RoomUser, Long> {

    RoomUser save(RoomUser roomUser);

    List<RoomUser> findAllByRoomId(Long roomId);

}
