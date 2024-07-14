package com.firstversion.socialmedia.service.impl;

import com.firstversion.socialmedia.config.CloudinaryService;
import com.firstversion.socialmedia.dto.request.CreatePostRequest;
import com.firstversion.socialmedia.dto.response.post.PostLikeResponse;
import com.firstversion.socialmedia.dto.response.post.PostResponse;
import com.firstversion.socialmedia.exception.NotFoundException;
import com.firstversion.socialmedia.exception.UserNotAuthorizedException;
import com.firstversion.socialmedia.model.entity.Post;
import com.firstversion.socialmedia.model.entity.PostLike;
import com.firstversion.socialmedia.model.entity.User;
import com.firstversion.socialmedia.repository.CommentRepository;
import com.firstversion.socialmedia.repository.PostLikeRepository;
import com.firstversion.socialmedia.repository.PostRepository;
import com.firstversion.socialmedia.repository.UserRepository;
import com.firstversion.socialmedia.security.jwt.JwtUtils;
import com.firstversion.socialmedia.service.PostService;
import com.firstversion.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    CommentRepository commentRepository;

    @Override
    public PostResponse createNewPost(CreatePostRequest createPostRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
//        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        Post post = new Post();
        String imageUrl = uploadImage(createPostRequest.getImage());
        String videoUrl = uploadVideo(createPostRequest.getVideo());
        post.setCaption(createPostRequest.getCaption());
        if (imageUrl != null) post.setImage(imageUrl);
        if (videoUrl != null) post.setVideo(videoUrl);
        post.setUser(user);
        Post savePost = postRepository.save(post);
        return savePost.toPostResponse();
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String imageUrl = null;
        if (image != null) {
            Map<String, Object> result = cloudinaryService.uploadImage(image);
            imageUrl = result.get("url").toString();
        }
        return imageUrl;
    }

    public String uploadVideo(MultipartFile src) throws IOException {
        String videoUrl = null;
        if (src != null) {
            Map<String, Object> result = cloudinaryService.uploadVideo(src);
            videoUrl = result.get("url").toString();
        }
        return videoUrl;
    }

    @Override
    @Transactional
    public void delete(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        if (user.getId() != post.getUser().getId()) {
            throw new UserNotAuthorizedException("User do not have permited");
        }
        String imageUrl = post.getImage();
        String videoUrl = post.getVideo();
        postRepository.deleteById(postId);
//        if(imageUrl != null){
//            cloudinaryService.delete(imageUrl);
//        }
    }

    @Override
    public List<PostResponse> findPostByUserId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundCurrenUserLogin = (User) authentication.getPrincipal();
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found.");
        }
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach((post -> postResponses.add(toPostResponse(post, foundCurrenUserLogin.getId()))));
        return postResponses;
    }

    @Override
    public PostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
//        List<UserResponse> userResponses = userLikeList.stream().map(User::toUserResponse).toList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundCurrenUserLogin = (User) authentication.getPrincipal();
        PostResponse response = toPostResponse(post, foundCurrenUserLogin.getId());
//        int totalLikes = postLikeRepository.countUserLikePost(postId);
//        int totalComments = commentRepository.countCommentByPost(postId);
//        response.setTotalComments(totalComments);
//        response.setTotalLikes(totalLikes);
//        response.setListUserLiked(userResponses);
        return response;
    }

    @Override
    public List<PostResponse> findAllPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User foundUser = (User) authentication.getPrincipal();
        List<Post> posts = postRepository.findAll();
        if (foundUser != null) {
            return posts.stream().map(post -> toPostResponse(post, foundUser.getId())).toList();
        }
        return posts.stream().map(this::toPostResponse).toList();
    }


    @Override
    public String savedPost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        String message = user.handleSaved_Unsaved(postId);
        userRepository.save(user);
        return message;
    }

    @Override
    public PostLikeResponse likePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found."));
        PostLike existingPostLike = postLikeRepository.findByUserAndPost(user.getId(), postId);
        if (existingPostLike != null) {
            existingPostLike.setDelete(!existingPostLike.isDelete());
            PostLike saved = postLikeRepository.save(existingPostLike);
            return saved.toPostLikeResponse();
        }
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUserLike(user);
        postLike.setDelete(false);
        PostLike saved = postLikeRepository.save(postLike);
        return saved.toPostLikeResponse();
    }

    private PostResponse toPostResponse(Post post, Long userId) {
        PostResponse response = new PostResponse();
        response.setCaption(post.getCaption());
        response.setId(post.getId());
        response.setImage(post.getImage());
        response.setVideo(post.getVideo());
        response.setCreateDate(post.getCreateDate());
        response.setModifiedDate(post.getModifiedDate());
        response.setUserResponse(post.getUser().toUserResponse());
        response.setTotalComments(commentRepository.countCommentByPost(post.getId()));
        response.setTotalLikes(postLikeRepository.countUserLikePost(post.getId()));
        if (postLikeRepository.findUserLikePost(post.getId(), userId).isPresent()) {
            response.setCurrentUserLikePost(true);
        } else {
            response.setCurrentUserLikePost(false);
        }
//        response.setCurrentUserLikePost(postLikeRepository.findUserLikePost(post.getId(), userId).isPresent());
        return response;
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
        response.setTotalComments(commentRepository.countCommentByPost(post.getId()));
        response.setTotalLikes(postLikeRepository.countUserLikePost(post.getId()));
        return response;
    }
}
