package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateUserRequest;
import com.firstversion.socialmedia.dto.response.user.FollowUserResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {
    void register(CreateUserRequest userRequest);

    List<UserResponse> getAllUser();

    UserResponse updateUser(CreateUserRequest createUserRequest);

    UserResponse findUserById(Long userId);

    UserResponse findUserByEmail(String email);

    List<UserResponse> searchUsers(String query);

    UserResponse findUserByJwt(String jwt);

    UserResponse findUserDetails();

    String doUploadAvatar(MultipartFile image) throws IOException;
}
