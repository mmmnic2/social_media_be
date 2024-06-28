package com.firstversion.socialmedia.service;

import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createNewPost(PostResponse postResponse, String jwt);

    void delete(Long postId, String jwt);

    List<PostResponse> findPostByUserId(Long userId);

    PostResponse findPostById(Long postId);

    List<PostResponse> findAllPost();

    String savedPost(Long postId, String jwt);

    PostLikeResponse likePost(Long postId, String jwt);
}
