package com.firstversion.socialmedia.dto.response.authenticate;

import com.firstversion.socialmedia.dto.BaseDTO;
import com.firstversion.socialmedia.model.enums.Gender;
import com.firstversion.socialmedia.model.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSuccessResponse extends BaseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Role role;

}
