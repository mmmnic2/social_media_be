package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateChatRequest;
import com.firstversion.socialmedia.dto.response.chat.ChatResponse;
import com.firstversion.socialmedia.model.entity.Chat;

import java.util.List;

public interface ChatService {
    ChatResponse createChat(Long userId, CreateChatRequest createChatRequest);

    ChatResponse createGroupChat(List<Long> userIds);

    ChatResponse findChatById(Long chatId);

    List<ChatResponse> findAllChatByUser(Long userId);
}
