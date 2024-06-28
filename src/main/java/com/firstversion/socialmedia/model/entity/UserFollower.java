package com.firstversion.socialmedia.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(UserFollowerId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFollower extends BaseEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed; // người được theo dõi
    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower; // người theo dõi
}
