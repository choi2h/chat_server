package com.ffs.chat.controller;

import com.ffs.chat.dto.request.NotificationRequest;
import com.ffs.chat.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * 알림 테스트용 임시 컨트롤러*
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    public ResponseEntity<?> pushMessage(@RequestBody NotificationRequest request) throws IOException {
        log.info("Recieve request for notification. request={}", request);

        firebaseCloudMessageService.sendMessageTo(
                request.getToken(),
                request.getTitle(),
                request.getBody()
        );

        return ResponseEntity.ok().build();
    }
}
