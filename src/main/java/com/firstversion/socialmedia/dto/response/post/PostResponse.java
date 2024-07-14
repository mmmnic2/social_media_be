package com.firstversion.socialmedia.dto.response.post;

import com.firstversion.socialmedia.dto.BaseDTO;
import com.firstversion.socialmedia.dto.response.comment.CommentResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostResponse extends BaseDTO {

    private Long id;

    private String caption;

    private String image;

    private String video;

    private UserResponse userResponse;

    private List<UserResponse> listUserLiked;

    private int totalLikes;

    private int totalComments;

    private boolean isCurrentUserLikePost;
}
