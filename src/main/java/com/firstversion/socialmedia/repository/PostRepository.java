package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.user.id=:userId")
    List<Post> findByUserId(Long userId);


}
