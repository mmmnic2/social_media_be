package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.request.CreateMessageRequest;
import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.Chat;
import com.firstversion.socialmedia.model.entity.ChatMember;
import com.firstversion.socialmedia.model.entity.Message;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.ChatMemberRepository;
import com.firstversion.socialmedia.repository.ChatRepository;
import com.firstversion.socialmedia.repository.MessageRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatMemberRepository chatMemberRepository;

    @Override
    public MessageResponse createMessage(User user, CreateMessageRequest request) {
//        User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
        Chat foundChat = chatRepository.findById(request.getChatId()).orElseThrow(() -> new NotFoundException("Chat not found."));
        //check xem userId có thuộc đoạn chat hay không, nếu có thì tạo được message, nếu không thì ném lỗi
        ChatMember foundChatMember = chatMemberRepository.findByChatAndMember(request.getChatId(), user.getId()).orElseThrow(() -> new RuntimeException("You do not have permission to send a message to this chat."));
        Message createMess = new Message();
        createMess.setContent(request.getContent());
        if (request.getImage() != null) createMess.setImage(request.getImage());
        createMess.setChat(foundChat);
        createMess.setSender(user);
        return messageRepository.save(createMess).toMessageResponse();
    }

    @Override
    public List<MessageResponse> findMessageByChat(Long chatId) {
        return messageRepository.findByChatId(chatId).stream().map(Message::toMessageResponse).toList();
    }
}
