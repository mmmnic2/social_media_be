package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.request.CreateChatRequest;
import com.firstversion.socialmedia.dto.response.chat.ChatResponse;
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
import java.util.Objects;

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
    public ChatResponse createChat(Long userId, CreateChatRequest createChatRequest) {
        Chat foundChat = !Objects.equals(userId, createChatRequest.getUserId()) ?
                chatRepository.findExistedChat(userId, createChatRequest.getUserId()) :
                chatRepository.findExistedChatThemselves(userId);
//        Chat foundChat =
//                chatRepository.findExistedChat(userId, createChatRequest.getUserId());

        if (foundChat != null) return toChatResponse(foundChat);
        Chat saveChat = chatRepository.save(new Chat());
        List<Long> userList = new ArrayList<>();
        userList.add(userId);
        if (!Objects.equals(userId, createChatRequest.getUserId()))
            userList.add(createChatRequest.getUserId());
        for (Long id : userList) {
            User foundUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
            ChatMember cm = new ChatMember();
            cm.setChat(saveChat);
            cm.setMember(foundUser);
            cm.setNickname(foundUser.getFirstName() + " " + foundUser.getLastName());
            ChatMember saveCM = chatMemberRepository.save(cm);
        }
        return toChatResponse(saveChat);
    }

    @Transactional
    @Override
    public ChatResponse createGroupChat(List<Long> userIds) {
        Chat saveChat = chatRepository.save(new Chat());
        for (Long userId : userIds) {
            User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
            ChatMember cm = new ChatMember();
            cm.setChat(saveChat);
            cm.setMember(foundUser);
            cm.setNickname(foundUser.getFirstName() + foundUser.getLastName());
            ChatMember saveCM = chatMemberRepository.save(cm);
        }
        return toChatResponse(saveChat);
    }

    @Override
    public ChatResponse findChatById(Long chatId) {
        Chat foundChat = chatRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat not found."));
        return toChatResponse(foundChat);
    }

    @Override
    public List<ChatResponse> findAllChatByUser(Long userId) {
        List<Chat> foundChatList = chatRepository.findByUserId(userId);
        return foundChatList.stream().map(this::toChatResponse).toList();
    }

    public ChatResponse toChatResponse(Chat chat) {
        ChatResponse response = new ChatResponse();
        response.setChatId(chat.getChatId());
        response.setChatName(chat.getChatName());
        response.setChatImage(chat.getChatImage());
        List<ChatMember> chatMembers = chatMemberRepository.findByChatId(chat.getChatId());
        response.setMemberList(chatMembers.stream().map(chatMember -> chatMember.getMember().toUserResponse()).toList());
        return response;
    }
}
