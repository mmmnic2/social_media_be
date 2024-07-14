package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.request.CommentRequest;
import com.firstversion.socialmedia.dto.response.comment.CommentLikeResponse;
import com.firstversion.socialmedia.dto.response.comment.CommentResponse;
import com.firstversion.socialmedia.service.CommentLikeService;
import com.firstversion.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentLikeService commentLikeService;

    @PostMapping("/create-comment/post/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId,
                                           @RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse response = commentService.createComment(commentRequest, postId);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId) {
        try {
            CommentLikeResponse response = commentLikeService.likeComment(commentId);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to like a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse response = commentService.updateComment(commentRequest);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-post/{postId}")
    public ResponseEntity<?> getCommentListByPostId(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.findCommentByPostId(postId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllComments() {
        List<CommentResponse> responses = commentService.getAllComments();
        return ResponseEntity.ok(responses);
    }
}
