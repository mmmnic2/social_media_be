package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.response.story.StoryResponse;

import java.util.List;

public interface StoryService {
    StoryResponse createStory(StoryResponse storyResponse, String email);
    List<StoryResponse> findStoryByUserId(Long userId);
}
