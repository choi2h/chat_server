package com.ffs.chat.controller;


import com.ffs.chat.dto.ChatMessageDto;
import com.ffs.chat.service.broker.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

//    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final RedisPublisher redisPublisher;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDto message){
        log.info("{} entered client in {}.", message.getWriter(), message.getRoomId());

        // TODO Enum 클래스로 메시지 분리
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");

        redisPublisher.publish(message.getRoomId(), message);
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto message){
        log.info("Send message in {}. client={}", message.getRoomId(), message.getWriter());

        redisPublisher.publish(message.getRoomId(), message);
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}