package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.CommentLike;
import com.firstversion.socialmedia.model.entity.PostLike;
import com.firstversion.socialmedia.model.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,PostLikeId > {
    @Query("select pl from PostLike pl where pl.userLike.id=:userId and pl.post.id=:postId")
    PostLike findByUserAndComment(Long userId, Long postId);
}
