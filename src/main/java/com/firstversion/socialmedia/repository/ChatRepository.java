package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    //        @Query(value = "Select c.* from chat c " +
//            "where c.chat_id in (select chat_id from chat_member cm1 join chat_member cm2 " +
//            "on cm1.chat_id = cm2.chat_id " +
//            "where cm1.user_id=:user_id_1 and cm2.user_id=:user_id_2)", nativeQuery = true)
    @Query(value = "Select c.* from chat c where " +
            "c.chat_id in (select chat_id from chat_member cm where " +
            "cm.user_id in (:user_id_1, :user_id_2) " +
            "group by chat_id having count(distinct user_id) = 2)", nativeQuery = true)
    Chat findExistedChat(Long user_id_1, Long user_id_2);

    @Query(value = "Select c.* from chat c where " +
            "c.chat_id in (select chat_id from chat_member cm " +
//            "where cm.user_id = :user_id " +
            "group by chat_id having count(distinct user_id) = 1)", nativeQuery = true)
    Chat findExistedChatThemselves(Long user_id);
    @Query(value = "Select c.* from chat c where " +
            "c.chat_id in (select chat_id from chat_member cm where cm.user_id = :userId)"
            , nativeQuery = true)
    List<Chat> findByUserId(Long userId);

}
