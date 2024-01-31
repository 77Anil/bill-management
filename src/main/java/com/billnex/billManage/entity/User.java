package com.billnex.billManage.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Document(collection = "students")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private UserStatus status;

    private Role role;

    private String name;

    private String department;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * TODO: Add based on user status
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
