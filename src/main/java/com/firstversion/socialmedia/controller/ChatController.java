package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.request.CreateChatRequest;
import com.firstversion.socialmedia.dto.response.chat.ChatResponse;
import com.firstversion.socialmedia.model.entity.Chat;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping("/create-chat")
    public ResponseEntity<?> createChat(@RequestBody CreateChatRequest createChatRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
        ChatResponse response = chatService.createChat(foundUser.getId(), createChatRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getChatById(@PathVariable Long chatId) {
        ChatResponse response = chatService.findChatById(chatId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<?> findChatByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
        List<ChatResponse> responses = chatService.findAllChatByUser(foundUser.getId());
        return ResponseEntity.ok(responses);
    }
}
