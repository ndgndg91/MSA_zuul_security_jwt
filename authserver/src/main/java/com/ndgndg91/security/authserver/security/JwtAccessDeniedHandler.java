package com.ndgndg91.security.authserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndgndg91.security.authserver.model.api.response.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ndgndg91.security.authserver.model.api.response.ApiResult.ERROR;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    static ApiResult E403 = ERROR("Authentication error (cause: forbidden)", HttpStatus.FORBIDDEN);

    private ObjectMapper om;

    public JwtAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(E403));
        response.getWriter().flush();
        response.getWriter().close();
    }

}