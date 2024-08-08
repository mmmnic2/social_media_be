package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreateMessageRequest;
import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import com.firstversion.socialmedia.model.entity.Message;
import com.firstversion.socialmedia.model.entity.User;

import java.util.List;

public interface MessageService {
    MessageResponse createMessage(User user, CreateMessageRequest request);

    List<MessageResponse> findMessageByChat(Long chatId);
}
