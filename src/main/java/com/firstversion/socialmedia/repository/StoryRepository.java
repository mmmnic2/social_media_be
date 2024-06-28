package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    @Query("Select s from Story s where s.user.id=:userId")
    List<Story> findByUserId(Long userId);
}
