package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.request.CommentRequest;
import com.firstversion.socialmedia.dto.response.comment.CommentResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.exception.UserNotAuthorizedException;
import com.firstversion.socialmedia.model.entity.Comment;
import com.firstversion.socialmedia.model.entity.Post;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.CommentLikeRepository;
import com.firstversion.socialmedia.repository.CommentRepository;
import com.firstversion.socialmedia.repository.PostRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CommentLikeRepository commentLikeRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
//        User foundUser = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setVideo(commentRequest.getVideo());
        comment.setImage(commentRequest.getImage());
        comment.setUser(foundUser);
        comment.setPost(foundPost);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.toCommentResponse();
    }


    @Override
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(Comment::toCommentResponse).toList();
    }

    @Override
    public CommentResponse updateComment(CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
//        email = jwtUtils.extractUsername(email);
//        User foundUser = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Comment existingComment = commentRepository.findById(commentRequest.getId()).orElseThrow(() -> new NotFoundException("Comment not found."));
        existingComment.setContent(commentRequest.getContent() != null ? commentRequest.getContent() : existingComment.getContent());
        existingComment.setVideo(commentRequest.getVideo() != null ? commentRequest.getVideo() : existingComment.getVideo());
        existingComment.setImage(commentRequest.getImage() != null ? commentRequest.getImage() : existingComment.getImage());
        if (foundUser.getId() != existingComment.getUser().getId()) {
            throw new UserNotAuthorizedException("User does not have permited.");
        }
        Comment savedComment = commentRepository.save(existingComment);
        return savedComment.toCommentResponse();
    }

    @Override
    public List<CommentResponse> findCommentByPostId(Long postId) {
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(Comment::toCommentResponse).toList();
    }


}
