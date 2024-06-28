package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.response.comment.CommentLikeResponse;

public interface CommentLikeService {
    CommentLikeResponse likeComment(String email, Long commentId);
}
