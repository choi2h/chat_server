package com.ffs.chat.config;

import com.ffs.chat.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public IdGenerator getIdGenerator() {
        return new IdGenerator();

    }
}
