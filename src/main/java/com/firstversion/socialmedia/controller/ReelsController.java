package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.response.reels.ReelsResponse;
import com.firstversion.socialmedia.service.ReelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reels")
public class ReelsController {
    @Autowired
    private ReelsService reelsService;

    @PostMapping("/create")
    public ResponseEntity<?> createReels(@RequestBody ReelsResponse reelsResponse,
                                         @RequestHeader("Authorization") String jwt) {
        try {
            ReelsResponse response = reelsService.createReel(reelsResponse, jwt.substring(7));
            if (response != null) {
                return ResponseEntity.ok(response);
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create a reel.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            List<ReelsResponse> responses = reelsService.findAllReels();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findReelsByUser(@PathVariable Long userId){
        try {
            List<ReelsResponse> responses = reelsService.findReelByUser(userId);
            if (responses != null) {
                return ResponseEntity.ok(responses);
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create a reel.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
