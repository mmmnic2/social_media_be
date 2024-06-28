package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CommentRequest;
import com.firstversion.socialmedia.dto.response.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest, Long postId, String email);

    List<CommentResponse> getAllComments();

    CommentResponse updateComment(CommentRequest commentRequest, String email);

}
