package com.ffs.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Slf4j
@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chatGet(Model model) {
        log.info("ChatController, chat GET()");

        model.addAttribute("name", UUID.randomUUID().toString());
        return "chat";
    }
}
