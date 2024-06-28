package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query("select cmtl from CommentLike cmtl where cmtl.user.id=:userId and cmtl.comment.id=:commentId")
    CommentLike findByUserAndComment(Long userId, Long commentId);
}
