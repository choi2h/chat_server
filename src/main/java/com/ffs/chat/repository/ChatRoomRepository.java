package com.ffs.chat.repository;

import com.ffs.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends Repository<ChatRoom, Long> {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findByRoomId(Long roomId);

    @Query("SELECT c FROM ChatRoom c " +
            "JOIN RoomUser r " +
            "ON c.roomId = r.roomId " +
            "WHERE r.userId = :userId")
    List<ChatRoom> findByMemberId(Long userId);
}
