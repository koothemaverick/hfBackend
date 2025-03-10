package com.ll.hfback.domain.group.chatRoom.controller;

import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 모든 모임채팅방 조회
    @GetMapping("/chat-rooms")
    public Page<DetailChatRoomDto> getAllRooms(Pageable pageable){
        return chatRoomService.findAll(pageable);
    }

    // 모임채팅방 검색
    @GetMapping("/chat-rooms/search")
    public Page<DetailChatRoomDto> searchRooms(@RequestParam("keyword") String keyword, Pageable pageable){
        return chatRoomService.searchByKeyword(keyword, pageable);
    }

    // 해당 게시글의 모든 모임채팅방 조회
    @GetMapping("/chat-rooms/{festival-id}")
    public Page<DetailChatRoomDto> getRooms(@PathVariable("festival-id") String festivalId, Pageable pageable) {
        return chatRoomService.searchByFestivalId(festivalId, pageable);
    }

    // 해당 게시글에 모임채팅방 생성
    @PostMapping("/chat-rooms/{festival-id}")
    public ResponseEntity<String> createRoom(@PathVariable("festival-id") String festivalId, @RequestBody @Valid CreateChatRoomForm createChatRoomForm, @LoginUser Member loginUser) {
        chatRoomService.createChatRoom(festivalId, createChatRoomForm, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("모임이 성공적으로 만들어졌습니다.");
    }

    // 해당 모임채팅방 수정(방장만 가능)
    @PostMapping("/update-chat-room/{chat-room-id}")
    public ResponseEntity<String> updateRoom(@PathVariable("chat-room-id") String chatRoomId, @RequestBody @Valid UpdateChatRoomForm updateChatRoomForm, @LoginUser Member loginUser) {
        chatRoomService.updateChatRoom(chatRoomId, updateChatRoomForm, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("모임이 성공적으로 수정되었습니다.");
    }

    // 해당 모임채팅방에 참여신청
    @GetMapping("/apply-chat-room/{chat-room-id}")
    public ResponseEntity<String> applyChatRoom(@PathVariable("chat-room-id") String chatRoomId, @LoginUser Member loginUser) {
        chatRoomService.applyChatRoom(chatRoomId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 모임에 참여신청을 했습니다.");
    }

    // 해당 모임채팅방에 참여신청 취소
    @GetMapping("/cancel-apply-chat-room/{chat-room-id}")
    public ResponseEntity<String> cancelApplyChatRoom(@PathVariable("chat-room-id") String chatRoomId, @LoginUser Member loginUser) {
        chatRoomService.cancelApplyChatRoom(chatRoomId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 모임 참여신청을 취소했습니다.");
    }

    // 해당 모임채팅방 참여신청 승인
    @GetMapping("/approve-apply-chat-room/{chat-room-id}/{apply-member-id}")
    public ResponseEntity<String> approveApplyChatRoom(@PathVariable("chat-room-id") String chatRoomId, @PathVariable("apply-member-id") String applyMemberId, @LoginUser Member loginUser) {
        chatRoomService.approveApplyChatRoom(chatRoomId, applyMemberId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 참여신청을 승인했습니다.");
    }

    // 해당 모임채팅방 참여신청 거절
    @GetMapping("/refuse-apply-chat-room/{chat-room-id}/{apply-member-id}")
    public ResponseEntity<String> refuseApplyChatRoom(@PathVariable("chat-room-id") String chatRoomId, @PathVariable("apply-member-id") String applyMemberId, @LoginUser Member loginUser) {
        chatRoomService.refuseApplyChatRoom(chatRoomId, applyMemberId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 참여신청을 거절했습니다.");
    }

    // 해당 모임채팅방의 참여자 강퇴
    @GetMapping("/unqualify-chat-room/{chat-room-id}/{member-id}")
    public ResponseEntity<String> unqualifyChatRoom(@PathVariable("chat-room-id") String chatRoomId, @PathVariable("member-id") String unqualifyMemberId, @LoginUser Member loginUser) {
        chatRoomService.unqualifyChatRoom(chatRoomId, unqualifyMemberId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 사용자를 강퇴했습니다.");
    }

    // 해당 모임채팅방 나가기(방장이 나가는 경우 해당 모임채팅방 삭제)
    @GetMapping("/leave-chat-room/{chat-room-id}")
    public ResponseEntity<String> leaveChatRoom(@PathVariable("chat-room-id") String chatRoomId, @LoginUser Member loginUser) {
        chatRoomService.leaveChatRoom(chatRoomId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 모임채팅방을 나갔습니다.");
    }

    // 해당 모임채팅방 삭제(관리자 권한)
    @GetMapping("/delete-chat-room/{chat-room-id}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable("chat-room-id") String chatRoomId, @LoginUser Member loginUser) {
        chatRoomService.deleteChatRoom(chatRoomId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 모임채팅방을 삭제했습니다.");
    }

    // 해당 모임채팅방에서 참여자에게 방장권한 위임
    @GetMapping("/delegate-chat-room/{chat-room-id}/{member-id}")
    public ResponseEntity<String> delegateChatRoom(@PathVariable("chat-room-id") String chatRoomId, @PathVariable("member-id") String delegateMemberId, @LoginUser Member loginUser) {
        chatRoomService.delegateChatRoom(chatRoomId, delegateMemberId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 방장권한을 위임했습니다.");
    }
}
