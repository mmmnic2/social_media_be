package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.request.CreateUserRequest;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import com.firstversion.socialmedia.exception.AlreadyExistException;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.model.enums.Gender;
import com.firstversion.socialmedia.model.enums.Role;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void register(CreateUserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new AlreadyExistException("Email has already existed");
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setGender(Gender.valueOf(userRequest.getGender()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(UserMapper(user)));
        return userResponses;
    }

    @Override
    public void updateUser(String jwt, CreateUserRequest createUserRequest) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).get();
        if (createUserRequest.getEmail() != null) {
            if (userRepository.existsByEmail(createUserRequest.getEmail())) {
                throw new AlreadyExistException("Email has already existed.");
            }
            user.setEmail(createUserRequest.getEmail());
        }
        if (createUserRequest.getPassword() != null) {
            if (passwordEncoder.encode(createUserRequest.getPassword()).equals(user.getPassword())) {
                throw new AlreadyExistException("Password must be changed different with the old password");
            } else {
                user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            }
        }
        user.setFirstName(createUserRequest.getFirstName() != null ? createUserRequest.getFirstName() : user.getFirstName());
        user.setLastName(createUserRequest.getLastName() != null ? createUserRequest.getLastName() : user.getLastName());
        user.setGender(createUserRequest.getGender() != null ? Gender.valueOf(createUserRequest.getGender()) : user.getGender());
        userRepository.save(user);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Id is not existed."));
        return UserMapper(user);
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("Email is not existed."));
        return UserMapper(user);
    }


    @Override
    public List<UserResponse> searchUsers(String query) {
        List<User> users = userRepository.searchUser(query);
        if (users == null)
            return null;
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach((user -> userResponses.add(UserMapper(user))));
        return userResponses;
    }

    @Override
    public UserResponse findUserByJwt(String jwt) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).get();
        return UserMapper(user);
    }

    public UserResponse UserMapper(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setGender(String.valueOf(user.getGender()));
        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundException("User not found."));
    }
}
