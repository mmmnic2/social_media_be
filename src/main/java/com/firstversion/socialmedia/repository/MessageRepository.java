package com.firstversion.socialmedia.repository;

import com.firstversion.socialmedia.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "Select * from message where chat_id=:chatId", nativeQuery = true)
    List<Message> findByChatId(Long chatId);
}
