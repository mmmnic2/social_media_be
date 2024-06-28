package com.firstversion.socialmedia.dto.response.comment;

import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeResponse {
    private Long commentLikeId;
    private UserResponse userResponse;
    private CommentResponse commentResponse;
}
