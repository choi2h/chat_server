package com.ffs.chat.service.impl;

import com.ffs.chat.dto.ChatRoomDto;
import com.ffs.chat.dto.MatchingResult;
import com.ffs.chat.dto.request.CreateChatRoomRequest;
import com.ffs.chat.dto.response.CreateChatRoomResponse;
import com.ffs.chat.model.ChatRoom;
import com.ffs.chat.model.RoomUser;
import com.ffs.chat.repository.ChatRoomRepository;
import com.ffs.chat.repository.TopicRepository;
import com.ffs.chat.service.ChatRoomService;
import com.ffs.chat.service.RoomUserService;
import com.ffs.chat.service.SendManageServerRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final SendManageServerRequestService manageServerRequestService;
    private final RoomUserService roomUserService;
    private final TopicRepository topicRepository;

    // 새로운 채팅룸 생성
    @Override
    public CreateChatRoomResponse createChatRoom(CreateChatRoomRequest request) {
        Long myId = request.getMyId();
        Long targetId = request.getTargetId();

        MatchingResult matchingResult = manageServerRequestService
                .checkMemberMatchingWithEmployee(targetId, myId);
        if(matchingResult == null) {
            //TODO ERROR 권한 문제
            throw new IllegalStateException("NOT_HAVE_PERMISSION_TO_MEMBER");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        ChatRoom chatRoom = new ChatRoom(currentTime);
        chatRoomRepository.save(chatRoom);

        Long roomId = chatRoom.getRoomId();
        String memberName = matchingResult.getMemberName();
        roomUserService.getNewRoomUser(roomId, targetId, memberName);

        String employeeName = matchingResult.getEmployeeName();
        roomUserService.getNewRoomUser(roomId, myId, employeeName);

        topicRepository.registerChannelTopic(roomId);

        return CreateChatRoomResponse.builder()
                .roomId(roomId)
                .writerId(myId)
                .writerName(employeeName)
                .targetUserId(targetId)
                .targetUserName(memberName).build();
    }

    // 회원이 해당하는 채팅룸 조회
    public List<ChatRoomDto> findAllRooms(Long userId) {
        List<ChatRoom> rooms = chatRoomRepository.findByMemberId(userId);
        log.info("Find all rooms. userId={} count={}", userId, rooms.size());

        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        for(ChatRoom chatRoom : rooms) {
            ChatRoomDto chatRoomDto = convertChatRoomToChatRoomDto(chatRoom, userId);
            chatRoomDtoList.add(chatRoomDto);
        }

        return chatRoomDtoList;
    }

    private ChatRoomDto convertChatRoomToChatRoomDto(ChatRoom chatRoom, Long userId) {
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(chatRoom.getRoomId())
                .createAt(chatRoom.getCreatedAt())
                .build();


        List<RoomUser> roomUserList = chatRoom.getRoomUserList();
        for(RoomUser roomUser : roomUserList) {
            if(!roomUser.getUserId().equals(userId)) {
                chatRoomDto.setRoomName(roomUser.getUserName());
            }

            chatRoomDto.addUserInfo(roomUser.getUserId(), roomUser.getUserName());
        }

        return chatRoomDto;
    }

}
