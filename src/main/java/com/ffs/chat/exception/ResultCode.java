package com.ffs.chat.exception;

import org.springframework.http.HttpStatus;

public interface ResultCode<T> {

    T getCode();

    String getMessage();

    String getMessage(Object... variables);

    HttpStatus getStatus();

}
