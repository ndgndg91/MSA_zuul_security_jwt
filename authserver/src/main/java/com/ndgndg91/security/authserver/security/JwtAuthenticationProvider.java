package com.ndgndg91.security.authserver.security;

import com.ndgndg91.security.authserver.error.NotFoundException;
import com.ndgndg91.security.authserver.model.dto.UserDTO;
import com.ndgndg91.security.authserver.model.user.Role;
import com.ndgndg91.security.authserver.service.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JWT jwt;

    private final UserService userService;

    public JwtAuthenticationProvider(JWT jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(authenticationToken.authenticationRequest());
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            UserDTO user = userService.login(request.getPrincipal(), request.getCredentials());
            JwtAuthenticationToken authenticated =
                    new JwtAuthenticationToken(user.getSeq(), null, createAuthorityList(Role.USER.value()));
            String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
            authenticated.setDetails(new AuthenticationResult(apiToken, user));
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class, authentication);
    }

}