package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.comment.CommentLikeResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    private boolean isLiked;
    public CommentLikeResponse toCommentLikeResponse() {
        CommentLikeResponse response = new CommentLikeResponse();
        response.setCommentLikeId(this.getCommentLikeId());
        response.setUserResponse(this.getUser().toUserResponse());
        response.setCommentResponse(this.getComment().toCommentResponse());
        return response;
    }
}
