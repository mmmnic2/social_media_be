package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.response.user.FollowUserResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.model.entity.UserFollower;
import com.firstversion.socialmedia.repository.UserFollowerRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFollowerServiceImpl implements UserFollowerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFollowerRepository userFollowerRepository;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public String handleFollow_UnfollowUser(Long followedId, String token) {
        String email = jwtUtils.extractUsername(token);
        User followed = userRepository.findById(followedId).orElseThrow(() -> new NotFoundException("User Not Found."));
        User follower = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found."));
        UserFollower existingUserFollower = userFollowerRepository.findByFollowerIdAndFollowedId(follower.getId(), followedId);
        if (existingUserFollower != null) {
            //Nếu đã follow rồi thì unfollow
            userFollowerRepository.delete(existingUserFollower);
            return "Unfollow successfully.";
        } else {
            //Nếu chưa follow thì sẽ tạo follow mới
            UserFollower userFollower = new UserFollower(followed, follower);
            UserFollower saved = userFollowerRepository.save(userFollower);
            return "Follow successfully";
        }
    }

    // METHOD LẤY DANH SÁCH NGƯỜI THEO DÕI MÌNH
    @Override
    public List<FollowUserResponse> findListFollowerByUserId(String token) {
        String email = jwtUtils.extractUsername(token);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        List<UserFollower> followers = userFollowerRepository.findListUserFollowerByFollowedId(user.getId());
        List<FollowUserResponse> followerList = new ArrayList<>();
        if (!followers.isEmpty()) {
            followers.forEach(follower -> {
                FollowUserResponse followUserResponse = new FollowUserResponse(follower.getFollower().getId(),
                        follower.getFollower().getFirstName(), follower.getFollower().getLastName());
                followerList.add(followUserResponse);
            });
            return followerList;
        }

        return List.of();
    }

    //METHOD LẤY DANH SÁCH NGƯỜI MÌNH ĐANG THEO DÕI
    @Override
    public List<FollowUserResponse> findListFollowingByUserId(String token) {
        String email = jwtUtils.extractUsername(token);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        List<UserFollower> followings = userFollowerRepository.findListUserFollowerByFollowerId(user.getId());
        List<FollowUserResponse> followingList = new ArrayList<>();
        if (!followings.isEmpty()) {
            followings.forEach(following -> {
                FollowUserResponse followUserResponse = new FollowUserResponse(following.getFollowed().getId(),
                        following.getFollowed().getFirstName(), following.getFollowed().getLastName());
                followingList.add(followUserResponse);
            });
            return followingList;
        }
        return List.of();
    }
}
