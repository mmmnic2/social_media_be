package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.ChatMember;
import com.firstversion.socialmedia.model.entity.ChatMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, ChatMemberId> {
}
