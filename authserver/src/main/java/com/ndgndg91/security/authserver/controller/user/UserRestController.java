package com.ndgndg91.security.authserver.controller.user;

import com.ndgndg91.security.authserver.error.NotFoundException;
import com.ndgndg91.security.authserver.model.api.request.user.JoinRequest;
import com.ndgndg91.security.authserver.model.api.response.ApiResult;
import com.ndgndg91.security.authserver.model.api.response.user.JoinResult;
import com.ndgndg91.security.authserver.model.user.Role;
import com.ndgndg91.security.authserver.model.user.User;
import com.ndgndg91.security.authserver.security.JWT;
import com.ndgndg91.security.authserver.security.JwtAuthentication;
import com.ndgndg91.security.authserver.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.ndgndg91.security.authserver.model.api.response.ApiResult.OK;

@RestController
@RequestMapping("api")
public class UserRestController {

    private final JWT jwt;

    private final UserService userService;

    public UserRestController(JWT jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @PostMapping(path = "user/exists")
    public ApiResult<Boolean> checkEmail(
            @RequestBody
            Map<String, String> request
    ) {
        String email = request.get("address");
        return OK(userService.findByEmail(email).isPresent());
    }

    @PostMapping(path = "user/join")
    public ApiResult<JoinResult> join(@RequestBody JoinRequest joinRequest) {
        User user = userService.join(
                joinRequest.getName(),
                joinRequest.getPrincipal(),
                joinRequest.getCredentials()
        );
        String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
        return OK(new JoinResult(apiToken, user));
    }

    @GetMapping(path = "user/me")
    public ApiResult<User> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(
                userService.findById(authentication.id.value())
                        .orElseThrow(() -> new NotFoundException(User.class, authentication.id))
        );
    }

}
