package com.firstversion.socialmedia.controller;

import com.firstversion.socialmedia.dto.request.CreateMessageRequest;
import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping("/{chatId}")
    public ResponseEntity<?> findByChat(@PathVariable Long chatId) {
        List<MessageResponse> responses = messageService.findMessageByChat(chatId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/create-message")
    public ResponseEntity<?> createMessage(@RequestBody CreateMessageRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        MessageResponse response = messageService.createMessage(user, request);
        return ResponseEntity.ok(response);
    }

}
