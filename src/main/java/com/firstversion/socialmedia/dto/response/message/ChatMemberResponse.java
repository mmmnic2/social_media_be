package com.firstversion.socialmedia.dto.response.message;

import com.firstversion.socialmedia.dto.BaseDTO;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberResponse extends BaseDTO {
    private UserResponse memberResponse;
    private ChatResponse chatResponse;
    private String nickname;
}
