package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.request.CreateChatRequest;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.Chat;
import com.firstversion.socialmedia.model.entity.ChatMember;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.ChatMemberRepository;
import com.firstversion.socialmedia.repository.ChatRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatMemberRepository chatMemberRepository;

    @Transactional
    @Override
    public Chat createChat(Long userId, CreateChatRequest createChatRequest) {
        Chat foundChat = chatRepository.findExistedChat(userId, createChatRequest.getUserId());
        if (foundChat != null) return foundChat;
        Chat saveChat = chatRepository.save(new Chat());
        List<Long> userList = new ArrayList<>();
        userList.add(userId);
        userList.add(createChatRequest.getUserId());
        for (Long id : userList) {
            User foundUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
            ChatMember cm = new ChatMember();
            cm.setChat(saveChat);
            cm.setMember(foundUser);
            cm.setNickname(foundUser.getFirstName() + foundUser.getLastName());
            ChatMember saveCM = chatMemberRepository.save(cm);
        }
        return saveChat;
    }

    @Transactional
    @Override
    public Chat createGroupChat(List<Long> userIds) {
        Chat saveChat = chatRepository.save(new Chat());
        for (Long userId : userIds) {
            User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
            ChatMember cm = new ChatMember();
            cm.setChat(saveChat);
            cm.setMember(foundUser);
            cm.setNickname(foundUser.getFirstName() + foundUser.getLastName());
            ChatMember saveCM = chatMemberRepository.save(cm);
        }
        return saveChat;
    }

    @Override
    public Chat findChatById(Long chatId) {
        Chat foundChat = chatRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat not found."));
        return foundChat;
    }

    @Override
    public List<Chat> findAllChatByUser(Long userId) {
        List<Chat> foundChatList = chatRepository.findByUserId(userId);
        return foundChatList;
    }
}
