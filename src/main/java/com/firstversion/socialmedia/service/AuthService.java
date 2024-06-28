package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateUserRequest;
import com.firstversion.socialmedia.dto.request.LoginRequest;
import com.firstversion.socialmedia.dto.response.authenticate.AuthenticationResponse;
import com.firstversion.socialmedia.dto.response.authenticate.RegisterSuccessResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;

public interface AuthService {
    AuthenticationResponse authenticate(LoginRequest request);

    UserResponse register(CreateUserRequest userRequest);
}
