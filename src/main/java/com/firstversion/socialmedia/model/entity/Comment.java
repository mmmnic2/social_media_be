package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.comment.CommentResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String image;
    private String video;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public CommentResponse toCommentResponse() {
        CommentResponse response = new CommentResponse();
        response.setId(this.getId());
        response.setContent(this.getContent());
        response.setImage(this.getImage());
        response.setVideo(this.getVideo());
        response.setUserResponse(this.getUser().toUserResponse());
        response.setPostResponse(this.getPost().toPostResponse());
        return response;
    }
}
