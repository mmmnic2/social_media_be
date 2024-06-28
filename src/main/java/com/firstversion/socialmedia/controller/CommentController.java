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

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentLikeService commentLikeService;

    @PostMapping("/create-comment/post/{postId}")
    public ResponseEntity<?> createComment(@RequestHeader("Authorization") String jwt,
                                           @PathVariable Long postId,
                                           @RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse response = commentService.createComment(commentRequest, postId, jwt.substring(7));
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<?> likeComment(@RequestHeader("Authorization") String jwt,
                                         @PathVariable Long commentId) {
        try {
            CommentLikeResponse response = commentLikeService.likeComment(jwt.substring(7), commentId);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to like a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String jwt,
                                           @RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse response = commentService.updateComment(commentRequest, jwt.substring(7));
            if (response != null) {
                return ResponseEntity.ok(response);
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update a comment.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
