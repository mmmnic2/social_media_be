package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.response.story.StoryResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.Story;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.StoryRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public StoryResponse createStory(StoryResponse storyResponse, String email) {
//        email = jwtUtils.extractUsername(email);
        User foundUser = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Story story = new Story();
        story.setUser(foundUser);
        story.setCaptions(storyResponse.getCaptions());
        story.setVideo(storyResponse.getVideo());
        story.setImage(storyResponse.getImage());
        story.setTimeStamp(LocalDateTime.now());
        Story saved = storyRepository.save(story);
        return saved.toStoryResponse();
    }

    @Override
    public List<StoryResponse> findStoryByUserId(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
        List<Story> stories = storyRepository.findByUserId(userId);
        if (stories.isEmpty())
            return List.of();
        else
            return stories.stream().map(Story::toStoryResponse).toList();
    }
}
