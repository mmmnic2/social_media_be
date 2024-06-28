package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.exception.UserNotAuthorizedException;
import com.firstversion.socialmedia.model.entity.Post;
import com.firstversion.socialmedia.model.entity.PostLike;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.PostLikeRepository;
import com.firstversion.socialmedia.repository.PostRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.PostService;
import com.firstversion.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PostLikeRepository postLikeRepository;

    @Override
    public PostResponse createNewPost(PostResponse postResponse, String jwt) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post post = new Post();
        post.setCaption(postResponse.getCaption());
        post.setVideo(postResponse.getVideo());
        post.setImage(postResponse.getImage());
        post.setUser(user);
        Post savePost = postRepository.save(post);
        return toPostResponse(savePost);
    }

    @Override
    public void delete(Long postId, String jwt) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        if (user.getId() != post.getUser().getId()) {
            throw new UserNotAuthorizedException("User do not have permited");
        }
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> findPostByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found.");
        }
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach((post -> postResponses.add(toPostResponse(post))));
        return postResponses;
    }

    @Override
    public PostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        return toPostResponse(post);
    }

    @Override
    public List<PostResponse> findAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach((post -> postResponses.add(toPostResponse(post))));
        return postResponses;
    }

    @Override
    public String savedPost(Long postId, String jwt) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        String message = user.handleSaved_Unsaved(postId);
        userRepository.save(user);
        return message;
    }

    @Override
    public PostLikeResponse likePost(Long postId, String jwt) {
        String email = jwtUtils.extractUsername(jwt);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        PostLike existingPostLike = postLikeRepository.findByUserAndComment(user.getId(), postId);
        if (existingPostLike != null) {
            existingPostLike.setDelete(!existingPostLike.isDelete());
            PostLike saved = postLikeRepository.save(existingPostLike);
            return saved.toPostLikeResponse();
        }
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUserLike(user);
        PostLike saved = postLikeRepository.save(postLike);
        return saved.toPostLikeResponse();
    }

    private PostResponse toPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setCaption(post.getCaption());
        response.setId(post.getId());
        response.setImage(post.getImage());
        response.setVideo(post.getVideo());
        response.setCreateDate(post.getCreateDate());
        response.setModifiedDate(post.getModifiedDate());
        response.setUserResponse(post.getUser().toUserResponse());
        return response;
    }
}
