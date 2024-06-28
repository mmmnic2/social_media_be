package com.firstversion.socialmedia.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FollowUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
}
