package com.firstversion.socialmedia.dto.response.message;

import com.firstversion.socialmedia.dto.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse extends BaseDTO {
    private Long chatId;
    private String chatName;
    private String chatImage;
}
