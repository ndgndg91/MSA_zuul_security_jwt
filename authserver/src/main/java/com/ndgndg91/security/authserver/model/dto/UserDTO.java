package com.ndgndg91.security.authserver.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ndgndg91.security.authserver.model.user.User;
import com.ndgndg91.security.authserver.security.JWT;

import java.time.LocalDateTime;

public class UserDTO {
    private final Long seq;

    private final String name;

    private final String email;

    @JsonIgnore
    private String password;

    private int loginCount;

    private LocalDateTime lastLoginAt;

    private final LocalDateTime createAt;

    private UserDTO(User user) {
        seq = user.getSeq();
        name = user.getName();
        email = user.getEmail();
        createAt = user.getCreateAt();
    }

    public static UserDTO create(User user){
        UserDTO dto = new UserDTO(user);
        dto.loginCount = user.getLoginCount();
        if (user.getLastLoginAt().isPresent()) {
            dto.lastLoginAt = user.getLastLoginAt().get();
        }
        return dto;
    }

    public String newApiToken(JWT jwt, String[] roles) {
        JWT.Claims claims = JWT.Claims.of(seq, name, email, roles);
        return jwt.newToken(claims);
    }

    public Long getSeq() {
        return seq;
    }
}
