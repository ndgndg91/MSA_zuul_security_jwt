package com.ndgndg91.security.authserver.controller.user;

import com.ndgndg91.security.authserver.error.NotFoundException;
import com.ndgndg91.security.authserver.model.api.request.user.ChangePasswordRequest;
import com.ndgndg91.security.authserver.model.api.request.user.JoinRequest;
import com.ndgndg91.security.authserver.model.api.response.ApiResult;
import com.ndgndg91.security.authserver.model.api.response.user.JoinResult;
import com.ndgndg91.security.authserver.model.dto.UserDTO;
import com.ndgndg91.security.authserver.model.user.Role;
import com.ndgndg91.security.authserver.model.user.User;
import com.ndgndg91.security.authserver.security.JWT;
import com.ndgndg91.security.authserver.security.JwtAuthentication;
import com.ndgndg91.security.authserver.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.ndgndg91.security.authserver.model.api.response.ApiResult.OK;

@Slf4j
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
        log.info(joinRequest.getCredentials());
        UserDTO user = userService.join(
                joinRequest.getName(),
                joinRequest.getPrincipal(),
                joinRequest.getCredentials()
        );
        String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
        return OK(new JoinResult(apiToken, user));
    }

    @GetMapping(path = "user/me")
    public ApiResult<UserDTO> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(
                userService.findById(authentication.id.value())
                        .orElseThrow(() -> new NotFoundException(User.class, authentication.id))
        );
    }

    @PatchMapping(path = "user/updatePassWord")
    public ApiResult<UserDTO> updatePassWord(@AuthenticationPrincipal JwtAuthentication authentication,
                                     @RequestBody ChangePasswordRequest request){
        log.info(request.toString());
        log.info(authentication.toString());
        return OK(userService.updateAll(authentication, request));
    }

}
