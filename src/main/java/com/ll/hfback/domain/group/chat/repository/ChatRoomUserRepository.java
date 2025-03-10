package com.ll.hfback.domain.group.chat.repository;

import com.ll.hfback.domain.group.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.ll.hfback.domain.group.chat.repository
 * fileName       : MessageReadStatusRepository
 * author         : sungjun
 * date           : 2025-01-27
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-27        kyd54       최초 생성
 */
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    Optional<ChatRoomUser> findByChatRoomIdAndMemberId(Long chatRoomId, Long memberId);
    Optional<List<ChatRoomUser>> findAllByChatRoomId(Long chatRoomId);
    void deleteAllByChatRoomId(Long chatRoomId);
    Optional<List<ChatRoomUser>> findAllByMemberId(Long memberId);
}
