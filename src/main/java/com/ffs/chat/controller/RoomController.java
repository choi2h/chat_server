package com.ffs.chat.controller;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.dto.response.CreateChatRoomResponse;
import com.ffs.chat.dto.response.EnteredChatRoomResponse;
import com.ffs.chat.dto.response.SearchChatRoomsResponse;
import com.ffs.chat.service.ChatRoomService;
import com.ffs.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class RoomController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseEntity<Object> rooms(@RequestParam Long userId){
        log.info("Get all Chat Rooms");

        List<ChatRoomDto> rooms = chatRoomService.findAllRooms(userId);
        SearchChatRoomsResponse response = SearchChatRoomsResponse.builder().chatRooms(rooms).build();
        return ResponseEntity.ok().body(response);
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ResponseEntity<?> create(@RequestBody CreateChatRoomRequest request){
        log.info("Create Chat Room , name: " + request.getMyId());

        CreateChatRoomResponse response = chatRoomService.createChatRoom(request);

        return ResponseEntity.ok(response);
    }

    //채팅방 조회
    @GetMapping("/room")
    public ResponseEntity<Object> getRoom(@RequestParam Long roomId, Model model){
        log.info("Get chat room, roomID : " + roomId);

        String memberName = memberService.createMember();
        EnteredChatRoomResponse response = EnteredChatRoomResponse
                .builder()
                .roomId(roomId)
                .memberName(memberName)
                .build();

        return ResponseEntity.ok(response);
    }
}
