package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.PostLike;
import com.firstversion.socialmedia.model.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    @Query("select pl from PostLike pl where pl.userLike.id=:userId and pl.post.id=:postId")
    PostLike findByUserAndPost(Long userId, Long postId);

    // Hàm đếm số lượng like post
    @Query("select count(pl) from PostLike pl where pl.post.id = :postId and pl.isDelete = false")
    int countUserLikePost(Long postId);

    @Query("select pl from PostLike pl where pl.userLike.id=:userId and pl.post.id=:postId and pl.isDelete = false")
    Optional<PostLike> findUserLikePost(Long postId, Long userId);


}
