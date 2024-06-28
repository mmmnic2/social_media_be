package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateUserRequest;
import com.firstversion.socialmedia.dto.response.user.FollowUserResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(CreateUserRequest userRequest);

    List<UserResponse> getAllUser();

    void updateUser(String jwt, CreateUserRequest createUserRequest);

    UserResponse findUserById(Long userId);

    UserResponse findUserByEmail(String email);

    List<UserResponse> searchUsers(String query);

    UserResponse findUserByJwt(String jwt);

}
