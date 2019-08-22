package com.ndgndg91.security.authserver.security;

import com.ndgndg91.security.authserver.model.commons.Id;
import com.ndgndg91.security.authserver.model.user.User;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

    public final Id<User, Long> id;

    public final String name;

    public final String email;

    JwtAuthentication(Long id, String name, String email) {
        checkNotNull(id, "id must be provided.");
        checkNotNull(name, "name must be provided.");
        checkNotNull(email, "email must be provided.");

        this.id = Id.of(User.class, id);
        this.name = name;
        this.email = email;
    }

}