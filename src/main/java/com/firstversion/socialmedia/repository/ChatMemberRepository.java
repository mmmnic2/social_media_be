package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.ChatMember;
import com.firstversion.socialmedia.model.entity.ChatMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberId> {
    @Query("select cm from ChatMember cm where cm.chat.id = :chatId and cm.member.id = :memberId")
    Optional<ChatMember> findByChatAndMember(Long chatId, Long memberId);

    @Query("select cm from ChatMember cm where cm.chat.id = :chatId")
    List<ChatMember> findByChatId(Long chatId);

}
