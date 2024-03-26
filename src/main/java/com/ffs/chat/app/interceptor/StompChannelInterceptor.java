package com.ffs.chat.app.interceptor;

import com.ffs.chat.app.auth.JwtTokenProvider;
import com.ffs.chat.service.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

    private final PresenceService presenceService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.debug("Message : {}", message);
        log.debug("Header : {}", message.getHeaders());
        log.info("Stomp command : {}", Objects.requireNonNull(accessor.getCommand()).getMessageType());

        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();

        if(command == null) {
            log.error("Not has command of stomp.");
            throw new IllegalStateException("Not has command of stomp");
        }

        switch (command) {
            case CONNECT:
                log.debug("[WEBSOCKET_COMMAND] WebSocket connect. sessionId={}, token={}", sessionId,
                        accessor.getFirstNativeHeader("AT_Authorization"));

                String token = accessor.getFirstNativeHeader("AT_Authorization");
                String userId = jwtTokenProvider.getPayload(token);

                presenceService.connect(sessionId, userId);

            case SUBSCRIBE:
                log.debug("[WEBSOCKET_COMMAND] WebSocket subscribing. sessionId={}", sessionId);

                String destination = accessor.getDestination();
                if(destination != null && !destination.isEmpty()) {
                    String[] arr = accessor.getDestination().split("/");
                    String roomId = arr[arr.length-1];

                    presenceService.enter(sessionId, roomId);
                }

            case UNSUBSCRIBE:
                log.debug("[WEBSOCKET_COMMAND] WebSocket unsubscribing. sessionId={}", sessionId);
                presenceService.exit(sessionId);

            case DISCONNECT:
                log.debug("[WEBSOCKET_COMMAND] WebSocket disconnect. sessionId={}", sessionId);
                presenceService.disconnect(sessionId);

        }

        return message;
    }
}
