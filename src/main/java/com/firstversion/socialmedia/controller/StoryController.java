package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.response.story.StoryResponse;
import com.firstversion.socialmedia.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/story")
public class StoryController {
    @Autowired
    StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<?> createStory(@RequestBody StoryResponse storyResponse) {
//        @RequestHeader("Authorization") String jwt
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            StoryResponse response = storyService.createStory(storyResponse, email);
            if (response != null)
                return ResponseEntity.ok(response);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create a story.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        try {
            List<StoryResponse> responses = storyService.findStoryByUserId(userId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
