package com.firstversion.socialmedia.dto.response.comment;

import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import com.firstversion.socialmedia.model.entity.Post;
import com.firstversion.socialmedia.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String image;
    private String video;
    private UserResponse userResponse;
    private PostResponse postResponse;
}
