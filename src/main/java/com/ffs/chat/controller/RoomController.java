package com.ffs.chat.controller;

import com.ffs.chat.repository.ChatRoomDtoRepository;
import com.ffs.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class RoomController {

    private final ChatRoomDtoRepository repository;
    private final MemberService memberService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(){
        log.info("Get all Chat Rooms");

        ModelAndView mv = new ModelAndView("rooms");
        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes attr){
        log.info("Create Chat Room , name: " + name);

        repository.createChatRoom(name);
        attr.addFlashAttribute("roomName", name);

        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(String roomId, Model model){
        log.info("Get chat room, roomID : " + roomId);

        model.addAttribute("room", repository.findRoomById(roomId));
        model.addAttribute("userName", memberService.createMember());
        return "room";
    }
}
