package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.UserFollower;
import com.firstversion.socialmedia.model.entity.UserFollowerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, UserFollowerId> {
    @Query("Select uf from UserFollower uf where uf.follower.id=:followerId and uf.followed.id=:followedId")
    UserFollower findByFollowerIdAndFollowedId(Long followerId,
                                               Long followedId);

    //Đây là danh sách followings
    @Query("select uf from UserFollower uf where uf.follower.id=:followerId")
    List<UserFollower> findListUserFollowerByFollowerId(Long followerId);

    //Đây là danh sách followers
    @Query("select uf from UserFollower uf where uf.followed.id=:followedId ")
    List<UserFollower> findListUserFollowerByFollowedId(Long followedId);
}
