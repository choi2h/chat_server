package com.ffs.chat.model;

import com.ffs.chat.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "connection_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConnectionLog {

    @Id
    private String id;
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
