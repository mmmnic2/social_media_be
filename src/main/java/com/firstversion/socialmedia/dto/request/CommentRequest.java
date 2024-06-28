package com.firstversion.socialmedia.dto.request;

import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private Long id;
    private String content;
    private String image;
    private String video;
}
