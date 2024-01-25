package com.ffs.chat.controller;

import com.ffs.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MemberService memberService;

    @GetMapping("/chat")
    public String chatGet(Model model) {
        log.info("ChatController, chat GET()");

        String id = memberService.createMember();
        model.addAttribute("name", id);
        return "chat";
    }
}
