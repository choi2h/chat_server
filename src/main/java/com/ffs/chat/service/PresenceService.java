package com.ffs.chat.service;

import org.springframework.stereotype.Service;


public interface PresenceService {

    // user 접속 처리
    void connect(String sessionId, String userId);

    void disconnect(String sessionId);

    // user 채팅방 입장
    void enter(String sessionId, String roomId);

    // user 채팅방 퇴장
    void exit(String sessionId);

    // user 채팅방 접속 여부 확인
    boolean isEnteredRoom(String sessionId, String roomId);

}
