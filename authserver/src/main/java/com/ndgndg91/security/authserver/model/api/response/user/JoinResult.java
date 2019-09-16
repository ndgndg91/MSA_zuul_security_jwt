package com.ndgndg91.security.authserver.model.api.response.user;

import com.ndgndg91.security.authserver.model.dto.UserDTO;

import static com.google.common.base.Preconditions.checkNotNull;


public class JoinResult {

    private final String apiToken;

    private final UserDTO user;

    public JoinResult(String apiToken, UserDTO user) {
        checkNotNull(apiToken, "apiToken must be provided.");
        checkNotNull(user, "user must be provided.");

        this.apiToken = apiToken;
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public UserDTO getUser() {
        return user;
    }

}
