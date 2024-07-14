package com.firstversion.socialmedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeId implements Serializable {
    private Long userLike;
    private Long post;
}
