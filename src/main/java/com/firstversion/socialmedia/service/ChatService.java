package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateChatRequest;
import com.firstversion.socialmedia.model.entity.Chat;

import java.util.List;

public interface ChatService {
    Chat createChat(Long userId, CreateChatRequest createChatRequest);

    Chat createGroupChat(List<Long> userIds);

    Chat findChatById(Long chatId);

    List<Chat> findAllChatByUser(Long userId);
}
