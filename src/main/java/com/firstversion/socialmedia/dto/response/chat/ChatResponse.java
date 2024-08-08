package com.firstversion.socialmedia.dto.response.chat;

import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private Long chatId;
    private String chatName;
    private String chatImage;
    private List<UserResponse> memberList;
}
