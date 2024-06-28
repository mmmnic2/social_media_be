package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.message.ChatMemberResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(ChatMemberId.class)
@Table(name = "chat_member")
public class ChatMember extends BaseEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User member;
    // biệt danh
    private String nickname;

    public ChatMemberResponse toChatMemberResponse() {
        ChatMemberResponse response = new ChatMemberResponse();
        response.setChatResponse(this.getChat().toChatResponse());
        response.setMemberResponse(this.getMember().toUserResponse());
        response.setNickname(this.getNickname());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        return response;
    }
}
