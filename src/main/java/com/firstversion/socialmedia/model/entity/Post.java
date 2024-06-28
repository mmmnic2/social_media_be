package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.post.PostResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter

public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String caption;
    @Column
    private String image;
    @Column
    private String video;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //user post bài viết


    public PostResponse toPostResponse() {
        PostResponse response = new PostResponse();
        response.setCaption(this.getCaption());
        response.setId(this.getId());
        response.setImage(this.getImage());
        response.setVideo(this.getVideo());
        response.setCreateDate(this.getCreateDate());
        response.setModifiedDate(this.getModifiedDate());
        response.setUserResponse(this.getUser().toUserResponse());
        return response;
    }

}
