package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.post.id =:postId")
    List<Comment> findByPostId(Long postId);

    //Hàm đếm số lượng comment theo bài post
    @Query("select count(c) from Comment c where c.post.id=:postId")
    int countCommentByPost(Long postId);
}
