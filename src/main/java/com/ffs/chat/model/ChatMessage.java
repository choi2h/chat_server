package com.ffs.chat.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Document(collection = "message")
public class ChatMessage {
    @Id
    private String messageId;
    private Long writerId;
    private String writerName;
    private Long roomId;
    private String content;
    private LocalDateTime sendTime;

}
