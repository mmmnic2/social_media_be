package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.response.user.FollowUserResponse;

import java.util.List;

public interface UserFollowerService {

    String handleFollow_UnfollowUser(Long followedId, String token);
    List<FollowUserResponse> findListFollowerByUserId(String token);
    List<FollowUserResponse> findListFollowingByUserId(String token);
}
