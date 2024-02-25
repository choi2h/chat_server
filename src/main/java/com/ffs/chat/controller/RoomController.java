package com.ffs.chat.controller;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.dto.response.EnteredChatRoomResponse;
import com.ffs.chat.dto.response.SearchChatRoomsResponse;
import com.ffs.chat.repository.ChatRoomDtoRepository;
import com.ffs.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private final ChatRoomDtoRepository repository;
    private final MemberService memberService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseEntity<Object> rooms(){
        log.info("Get all Chat Rooms");

        List<ChatRoomDto> rooms = repository.findAllRooms();
        SearchChatRoomsResponse response = SearchChatRoomsResponse.builder().chatRooms(rooms).build();
        return ResponseEntity.ok().body(response);
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ResponseEntity<?> create(@RequestBody CreateChatRoomRequest request){
        log.info("Create Chat Room , name: " + request.getName());

        repository.createChatRoom(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //채팅방 조회
    @GetMapping("/room")
    public ResponseEntity<Object> getRoom(@RequestParam String roomId, Model model){
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
