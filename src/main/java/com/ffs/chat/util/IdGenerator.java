package com.ffs.chat.util;


import java.util.UUID;

public class IdGenerator {

    public String createNewId(String prefix, String type) {
        StringBuilder builder = new StringBuilder();

        builder.append(prefix);
        builder.append("_");
        builder.append(type);
        builder.append("_");
        String uuid = getUUID();
        builder.append(uuid);

        return builder.toString();
    }

    private String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
