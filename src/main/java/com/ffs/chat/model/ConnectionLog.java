package com.ffs.chat.model;

import com.ffs.chat.util.DateTimeUtil;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "connection_log")
public class ConnectionLog {
    @Id
    private Long id;
    private String userId;
    private String accessDate;
    private String accessTime;
    private boolean connection;

    public ConnectionLog(String userId, LocalDateTime localDateTime, boolean connection) {
        this.userId = userId;
        this.accessDate = localDateTime.format(DateTimeUtil.DATE_FORMATTER);
        this.accessTime = localDateTime.format(DateTimeUtil.TIME_FORMATTER);
        this.connection = connection;
    }
}
