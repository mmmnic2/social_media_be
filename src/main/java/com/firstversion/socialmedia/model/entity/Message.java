package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private String content;
    private String image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public MessageResponse toMessageResponse() {
        MessageResponse response = new MessageResponse();
        response.setMessageId(this.getMessageId());
        response.setContent(this.getContent());
        response.setImage(this.getImage());
        response.setUserResponse(this.getSender().toUserResponse());
        response.setChatResponse(this.getChat().toChatResponse());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        return response;
    }
}
