package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.response.reels.ReelsResponse;
import com.firstversion.socialmedia.model.entity.Reels;

import java.util.List;

public interface ReelsService {
    ReelsResponse createReel(ReelsResponse reelsResponse, String email);
    List<ReelsResponse> findAllReels();
    List<ReelsResponse> findReelByUser(Long userId);
}
