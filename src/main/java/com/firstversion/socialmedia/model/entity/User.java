package com.firstversion.socialmedia.model.entity;

import com.firstversion.socialmedia.dto.response.user.UserResponse;
import com.firstversion.socialmedia.model.enums.Gender;
import com.firstversion.socialmedia.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Email(message = "Email is not valid")
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Gender gender;
    //List những bài post đã lưu
    @Column
    private List<Long> savedPost = new ArrayList<>();
    private Role role;
    @Column
    private String imageUrl;

    public String handleSaved_Unsaved(Long postId) {
        if (this.savedPost.contains(postId)) {
            this.savedPost.remove(postId);
            return "Unsave";
        } else {
            this.savedPost.add(postId);
            return "Save";
        }
    }

    public UserResponse toUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(this.getId());
        userResponse.setGender(String.valueOf(this.getGender()));
        userResponse.setEmail(this.getEmail());
        userResponse.setFirstName(this.getFirstName());
        userResponse.setLastName(this.getLastName());
        userResponse.setCreateDate(this.getCreateDate());
        userResponse.setModifiedDate(this.getModifiedDate());
        return userResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
