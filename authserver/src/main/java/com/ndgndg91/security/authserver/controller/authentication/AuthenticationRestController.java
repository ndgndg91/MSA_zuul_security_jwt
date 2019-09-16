package com.ndgndg91.security.authserver.controller.authentication;

import com.ndgndg91.security.authserver.error.UnauthorizedException;
import com.ndgndg91.security.authserver.model.api.response.ApiResult;
import com.ndgndg91.security.authserver.security.AuthenticationRequest;
import com.ndgndg91.security.authserver.security.AuthenticationResult;
import com.ndgndg91.security.authserver.security.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ndgndg91.security.authserver.model.api.response.ApiResult.OK;

@Slf4j
@RestController
@RequestMapping("api/user/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationRestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ApiResult<AuthenticationResult> authentication(@RequestBody AuthenticationRequest authRequest) throws UnauthorizedException {
        try {
            JwtAuthenticationToken authToken = new JwtAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return OK((AuthenticationResult) authentication.getDetails());
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

}
