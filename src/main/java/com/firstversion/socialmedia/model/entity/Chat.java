package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.message.ChatResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat")
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    private String chatName;
    private String chatImage;

    public ChatResponse toChatResponse() {
        ChatResponse response = new ChatResponse();
        response.setChatId(this.getChatId());
        response.setChatName(this.getChatName());
        response.setChatImage(this.getChatImage());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        return response;
    }
}
