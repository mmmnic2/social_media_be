package com.firstversion.socialmedia.model.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowerId implements Serializable {
    private Long followed;
    private Long follower;

}
