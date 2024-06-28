package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateMessageRequest;
import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import com.firstversion.socialmedia.model.entity.Message;

import java.util.List;

public interface MessageService {
    MessageResponse createMessage(Long userId, CreateMessageRequest request);

    List<MessageResponse> findMessageByChat(Long chatId);
}
