package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.request.CreateUserRequest;
import com.firstversion.socialmedia.dto.response.user.FollowUserResponse;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import com.firstversion.socialmedia.exception.AlreadyExistException;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.service.UserFollowerService;
import com.firstversion.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserFollowerService userFollowerService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser() {

        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String jwt,
                                        @RequestBody CreateUserRequest createUserRequest) {
        try {
            System.out.println(jwt);
            userService.updateUser(jwt.substring(7), createUserRequest);
            return ResponseEntity.ok("update user successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            UserResponse userResponse = userService.findUserById(userId);
            return ResponseEntity.ok(userResponse);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // follower là người theo dõi userId
    @PutMapping("/follow/{followedId}")
    public ResponseEntity<?> followUserHandle(@RequestHeader("Authorization") String jwt, @PathVariable Long followedId) {
        try {
            String message = userFollowerService.handleFollow_UnfollowUser(followedId, jwt.substring(7));
            return ResponseEntity.ok(message);
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email) {
        try {
            UserResponse response = userService.findUserByEmail(email);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    @GetMapping("/get-list-follower")
    public ResponseEntity<?> getListFollower(@RequestHeader("Authorization") String jwt) {
        List<FollowUserResponse> responses = userFollowerService.findListFollowerByUserId(jwt.substring(7));
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-list-following")
    public ResponseEntity<?> getListFollowing(@RequestHeader("Authorization") String jwt) {
        List<FollowUserResponse> responses = userFollowerService.findListFollowingByUserId(jwt.substring(7));
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String jwt) {
        UserResponse response = userService.findUserByJwt(jwt.substring(7));
        return ResponseEntity.ok(response);
    }
}
