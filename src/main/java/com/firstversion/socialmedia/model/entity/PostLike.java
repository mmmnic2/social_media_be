package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(PostLikeId.class)
@Table(name = "post_like")
public class PostLike extends BaseEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userLike;
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private boolean isDelete;

    public PostLikeResponse toPostLikeResponse() {
        return new PostLikeResponse(this.getPost().toPostResponse(), this.getUserLike().toUserResponse(), isDelete);
    }

}
