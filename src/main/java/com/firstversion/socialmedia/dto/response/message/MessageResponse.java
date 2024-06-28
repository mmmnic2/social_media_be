package com.firstversion.socialmedia.dto.response.message;

import com.firstversion.socialmedia.dto.BaseDTO;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import com.firstversion.socialmedia.model.entity.Chat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse extends BaseDTO {
    private Long messageId;
    private String content;
    private String image;
    private UserResponse userResponse;
    private ChatResponse chatResponse;
}
