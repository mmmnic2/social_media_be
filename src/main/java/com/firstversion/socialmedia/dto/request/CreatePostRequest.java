package com.firstversion.socialmedia.dto.request;

import com.firstversion.socialmedia.dto.BaseDTO;
import com.firstversion.socialmedia.dto.response.user.UserResponse;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest extends BaseDTO {
    private Long id;

    private String caption;

    private MultipartFile image;

    private MultipartFile video;

    private UserResponse userResponse;
}
