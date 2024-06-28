package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody PostResponse postResponse,
                                        @RequestHeader("Authorization") String jwt) {
        PostResponse response = postService.createNewPost(postResponse, jwt.substring(7));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@RequestHeader("Authorization") String jwt, @PathVariable Long postId) {
        postService.delete(postId, jwt.substring(7));
        return ResponseEntity.ok("Delete post successfully.");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        PostResponse response = postService.findPostById(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<?> findPostByUserId(@PathVariable Long userId) {
        List<PostResponse> responses = postService.findPostByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<PostResponse> responses = postService.findAllPost();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/saved-post/{postId}")
    public ResponseEntity<?> savedPost(@RequestHeader("Authorization") String jwt,
                                       @PathVariable Long postId) {
        String message = postService.savedPost(postId, jwt.substring(7));
        return ResponseEntity.ok(message + " post successfully.");
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@RequestHeader("Authorization") String jwt, @PathVariable Long postId) {
        PostLikeResponse response = postService.likePost(postId, jwt.substring(7));
        return ResponseEntity.ok(response);
    }
}
