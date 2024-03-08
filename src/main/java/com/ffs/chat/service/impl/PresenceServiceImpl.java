package com.ffs.chat.service.impl;

import com.ffs.chat.model.ConnectionLog;
import com.ffs.chat.repository.ConnectionLogRepository;
import com.ffs.chat.repository.PresenceRepository;
import com.ffs.chat.service.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private static final String USER_KEY_FORMAT = "user-%s";
    private static final String SESSION_KEY_FORMAT = "session-%s";
    private static final String ROBBY = "ROBBY";
    private static final String DISCONNECT = "disconnect";

    private final PresenceRepository presenceRepository;
    private final ConnectionLogRepository connectionLogRepository;

    @Override
    public void connect(String sessionId, String userId) {

        // 접속자 수 증가
        Long connectionCount = presenceRepository.increaseConnectionCount();
        log.debug("[INCREASE] Total connection user count : {}", connectionCount);

        // user session 정보 저장
        String sessionKey = String.format(SESSION_KEY_FORMAT, sessionId);
        presenceRepository.saveSessionInfo(sessionKey, userId);

        // user 접속 저장
        String userKey = String.format(USER_KEY_FORMAT, userId);
        presenceRepository.saveUserPresence(userKey, ROBBY);

        // mongoDB에 user 접속 기록 저장
        saveConnectionInfo(userId, true);
    }

    @Override
    public void disconnect(String sessionId) {
        // userId 조회
        String sessionKey = String.format(SESSION_KEY_FORMAT, sessionId);
        String userId = presenceRepository.getUserId(sessionKey);
        String userKey = String.format(USER_KEY_FORMAT, userId);

        // user session 및 접속 정보 초기화
        presenceRepository.saveSessionInfo(sessionKey, DISCONNECT);
        presenceRepository.saveUserPresence(userKey, DISCONNECT);

        // 접속자 수 감소
        Long connectionCount = presenceRepository.decreaseConnectionCount();
        log.debug("[DECREASE] Total connection user count : {}", connectionCount);

        // mongoDB에 user 접속 기록 저장
        saveConnectionInfo(userId, false);
    }

    @Override
    public void enter(String sessionId, String roomId) {
        // userId 조회
        String sessionKey = String.format(SESSION_KEY_FORMAT, sessionId);
        String userId = presenceRepository.getUserId(sessionKey);
        if(userId == null) {
            // TODO Exception 해당 세션에 대한 user 정보가 없음

        } else {
            // 사용자 접속 위치 저장
            String userKey = String.format(USER_KEY_FORMAT, userId);
            presenceRepository.saveUserPresence(userKey, roomId);

            log.debug("[Enter] user : {} , room : {}", userId, roomId);
        }
    }

    @Override
    public void exit(String sessionId) {
        // userId 조회
        String sessionKey = String.format(SESSION_KEY_FORMAT, sessionId);
        String userId = presenceRepository.getUserId(sessionKey);
        if(userId != null) {
            // 사용자 접속 위치 저장
            String userKey = String.format(USER_KEY_FORMAT, userId);
            presenceRepository.saveUserPresence(userKey, ROBBY);

            log.debug("[Exit] user : {}", userId);
        }
    }

    @Override
    public boolean isEnteredRoom(String userId, String roomId) {
        String userKey = String.format(USER_KEY_FORMAT, userId);
        String presenceRoomId = presenceRepository.getUserPresence(userKey);

        return presenceRoomId.equals(roomId);
    }

    private void saveConnectionInfo(String userId, boolean isConnection) {
        ConnectionLog connectionLog = makeConnectionLog(userId, isConnection);
        connectionLogRepository.save(connectionLog);
    }

    private ConnectionLog makeConnectionLog(String userId, boolean isConnection) {
        return new ConnectionLog(userId, LocalDateTime.now(), isConnection);
    }
}
