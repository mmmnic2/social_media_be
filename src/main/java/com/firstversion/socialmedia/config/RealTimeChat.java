package com.firstversion.socialmedia.config;

import com.firstversion.socialmedia.dto.response.message.MessageResponse;
import com.firstversion.socialmedia.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeChat {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    // giải thích cách sử dụng:
    // anotation  @MessageMapping để khi người dùng send message tới đường dẫn này thì sẽ gọi vào hàm sendToGroup
    // sau đó hàm này sẽ thực hiện việc gửi tin nhắn tới user thông qua convertAndSendToUser(),
    // tin nhắn sẽ được gửi tới kênh "user/{userId}/private" tùy vào ta setup
    // sau đó những user đăng kí kênh  "user/{userId}/private" sẽ nhận được tin nhắn

//    @MessageMapping("/group/{groupId}")
//    public MessageResponse sendToGroup(@Payload MessageResponse message, @DestinationVariable String groupId) {
//        System.out.println(message);
//        simpMessagingTemplate.convertAndSendToUser(groupId, "/private-group", message);
//        return message;
//    }

    @MessageMapping("/chat/{userId}")
    public MessageResponse sendtoUser(@Payload MessageResponse message, @DestinationVariable String userId) {
        simpMessagingTemplate.convertAndSendToUser(userId, "/private", message);
        return message;
    }

}
