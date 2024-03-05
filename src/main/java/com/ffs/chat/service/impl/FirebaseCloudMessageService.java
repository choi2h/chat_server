package com.ffs.chat.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffs.chat.model.FcmMessage;
import com.ffs.chat.model.Message;
import com.ffs.chat.model.Notification;
import com.ffs.chat.service.MessageService;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService implements MessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/fitnessfollow-1d8fa/messages:send";
    private final static String FIREBASE_CONFIG_PATH = "firebase/fitnessfollow-1d8fa-94919153529f.json";
    private final static String SCOPE_URL = "https://www.googleapis.com/auth/cloud-platform";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(Objects.requireNonNull(response.body()).string());

    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        Notification notification = Notification
                .builder()
                .title(title)
                .body(body)
                .build();

        Message message = Message
                .builder()
                .notification(notification)
                .token(targetToken)
                .build();

        FcmMessage fcmMessage = FcmMessage
                .builder()
                .message(message)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(List.of(SCOPE_URL));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
