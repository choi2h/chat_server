package com.ffs.chat.service;

import java.io.IOException;

public interface MessageService {
    void sendMessageTo(String targetToken, String title, String body) throws IOException;
}
