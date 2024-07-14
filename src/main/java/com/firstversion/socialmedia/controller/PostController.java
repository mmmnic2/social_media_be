package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.request.CreatePostRequest;
import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping(value = "/create-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(@RequestParam("caption") String caption,
                                        @RequestPart(value = "image", required = false) MultipartFile image,
                                        @RequestPart(value = "video", required = false) MultipartFile video) throws IOException {
        try {
            CreatePostRequest request = new CreatePostRequest();
            request.setCaption(caption);
            request.setVideo(video);
            request.setImage(image);
            PostResponse response = postService.createNewPost(request);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
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
    public ResponseEntity<?> savedPost(@PathVariable Long postId) {
        String message = postService.savedPost(postId);
        return ResponseEntity.ok(message + " post successfully.");
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        PostLikeResponse response = postService.likePost(postId);
        return ResponseEntity.ok(response);
    }
}
