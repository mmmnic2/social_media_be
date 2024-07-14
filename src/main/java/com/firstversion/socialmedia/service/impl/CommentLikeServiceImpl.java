package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.response.comment.CommentLikeResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.model.entity.Comment;
import com.firstversion.socialmedia.model.entity.CommentLike;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.CommentLikeRepository;
import com.firstversion.socialmedia.repository.CommentRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CommentLikeRepository commentLikeRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public CommentLikeResponse likeComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
//        User foundUser = userRepository.findUserByEmail(email).orElseThrow(()-> new NotFoundException("User not found."));
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found."));
        CommentLike existingCommentLike = commentLikeRepository.findByUserAndComment(foundUser.getId(), commentId);
        if (existingCommentLike != null) {
            commentLikeRepository.delete(existingCommentLike);
            return null;
        }
        CommentLike commentLike = new CommentLike();
        commentLike.setComment(foundComment);
        commentLike.setUser(foundUser);
        CommentLike saved = commentLikeRepository.save(commentLike);
        return saved.toCommentLikeResponse();
    }
}
