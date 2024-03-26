package com.ffs.chat.app.config;

import com.ffs.chat.util.DateTimeUtil;
import com.ffs.chat.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public IdGenerator getIdGenerator() {
        return new IdGenerator();
    }

    @Bean
    public DateTimeUtil getDateTimeUtil() {
        return new DateTimeUtil();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();

    }
}
