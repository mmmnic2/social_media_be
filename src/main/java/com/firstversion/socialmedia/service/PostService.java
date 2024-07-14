package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.request.CreatePostRequest;
import com.firstversion.socialmedia.dto.response.comment.CommentResponse;
import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostResponse createNewPost(CreatePostRequest createPostRequest) throws IOException;

    void delete(Long postId);

    List<PostResponse> findPostByUserId(Long userId);

    PostResponse findPostById(Long postId);

    List<PostResponse> findAllPost();

    String savedPost(Long postId);

    PostLikeResponse likePost(Long postId);

}
