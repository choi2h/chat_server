package com.ffs.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.debug("[ChatHandler] payload : {}", payload);

        for(WebSocketSession webSocketSession : list) {
            webSocketSession.sendMessage(message);
        }
    }

    /*클라이언트가 접속 시 호출되는 메서드*/

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        list.add(session);

        log.info("[ChatHandler] 클라이언트 접속 : {} ", session);
    }

    /*클라이언트가 접속 해제 시 호출되는 메서드*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        log.info("[ChatHandler] 클라이언트 접속 해제 : {}", session);
        list.remove(session);
    }
}
