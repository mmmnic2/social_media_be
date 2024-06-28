package com.firstversion.socialmedia.dto.response.post;

import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponse {
    private PostResponse postResponse;
    private UserResponse userResponse;
    private boolean isDelete;
}
