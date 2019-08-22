package com.ndgndg91.security.authserver.model.api.response.user;

import com.ndgndg91.security.authserver.model.user.User;

import static com.google.common.base.Preconditions.checkNotNull;


public class JoinResult {

    private final String apiToken;

    private final User user;

    public JoinResult(String apiToken, User user) {
        checkNotNull(apiToken, "apiToken must be provided.");
        checkNotNull(user, "user must be provided.");

        this.apiToken = apiToken;
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public User getUser() {
        return user;
    }

}
