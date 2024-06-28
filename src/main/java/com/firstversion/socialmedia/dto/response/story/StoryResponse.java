package com.firstversion.socialmedia.dto.response.story;

import com.firstversion.socialmedia.model.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {
    private Long id;
    private User user;
    private String image;
    private String video;
    private String captions;
    private LocalDateTime timeStamp;
}
