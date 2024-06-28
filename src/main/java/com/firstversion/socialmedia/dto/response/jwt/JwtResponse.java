package com.firstversion.socialmedia.dto.response.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
    private String message;
    public JwtResponse(Long id, String token, String message) {
        this.id = id;
        this.token = token;
        this.message = message;
    }
}
