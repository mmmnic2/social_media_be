package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.reels.ReelsResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reels { //story nổi bật
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String video;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReelsResponse toReelsResponse() {
        return new ReelsResponse(id, title, video, user.toUserResponse());
    }
}
