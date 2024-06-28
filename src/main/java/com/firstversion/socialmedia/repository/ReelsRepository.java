package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Reels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelsRepository extends JpaRepository<Reels, Long> {
    @Query(value = "Select * from reels where user_id= :userId", nativeQuery = true)
    public List<Reels> findByUserId(Long userId);
}
