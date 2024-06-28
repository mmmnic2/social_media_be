package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.response.reels.ReelsResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.Reels;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.ReelsRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.ReelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReelsServiceImpl implements ReelsService {
    @Autowired
    ReelsRepository reelsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ReelsResponse createReel(ReelsResponse reelsResponse, String email) {
        email = jwtUtils.extractUsername(email);
        User foundUser = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        Reels reels = new Reels();
        reels.setVideo(reelsResponse.getVideo());
        reels.setUser(foundUser);
        reels.setTitle(reelsResponse.getTitle());
        return reelsRepository.save(reels).toReelsResponse();
    }

    @Override
    public List<ReelsResponse> findAllReels() {
        List<Reels> responses = reelsRepository.findAll();
        if (responses.isEmpty()) {
            return List.of();
        }
        return responses.stream().map(Reels::toReelsResponse).toList();
    }

    @Override
    public List<ReelsResponse> findReelByUser(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
        List<Reels> responses = reelsRepository.findByUserId(userId);
        if(responses.isEmpty()) return List.of();
        return responses.stream().map(Reels::toReelsResponse).toList();
    }
}
