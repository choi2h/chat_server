package com.ffs.chat.repository;

import com.ffs.chat.model.RoomUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RoomUserRepository extends Repository<RoomUser, Long> {

    RoomUser save(RoomUser roomUser);

    List<RoomUser> findAllByRoomId(Long roomId);

    @Query("SELECT r1.roomId FROM RoomUser r1 " +
            "WHERE r1.userId = :userId1 AND r1.roomId IN " +
            "(SELECT r.roomId FROM RoomUser r WHERE r.userId = :userId2)")
    Long findRoomIdByRoomUsers(Long userId1, Long userId2);

}
