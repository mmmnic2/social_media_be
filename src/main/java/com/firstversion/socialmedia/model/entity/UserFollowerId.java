package com.firstversion.socialmedia.model.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowerId implements Serializable {
    private User followed;
    private User follower;

}
