package com.ndgndg91.security.authserver.service.user;

import com.ndgndg91.security.authserver.error.NotFoundException;
import com.ndgndg91.security.authserver.error.UnauthorizedException;
import com.ndgndg91.security.authserver.model.api.request.user.ChangePasswordRequest;
import com.ndgndg91.security.authserver.model.dto.UserDTO;
import com.ndgndg91.security.authserver.model.user.User;
import com.ndgndg91.security.authserver.repository.user.UserRepository;
import com.ndgndg91.security.authserver.security.JwtAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO join(String name, String email, String password) {
        checkArgument(isNotEmpty(password), "password must be provided.");
        checkArgument(
                password.length() >= 4 && password.length() <= 15,
                "password length must be between 4 and 15 characters."
        );
        checkArgument(!findByEmail(email).isPresent(), "Already Exists Email : ", email);

        User user = new User(name, email, passwordEncoder.encode(password));
        return UserDTO.create(save(user));
    }

    @Transactional
    public UserDTO login(String email, String password) {
        checkNotNull(password, "password must be provided.");

        User user = findByEmail(email)
                .orElseThrow(() -> new NotFoundException(User.class, email));
        user.login(passwordEncoder, password);
        user.afterLoginSuccess();
        save(user);
        return UserDTO.create(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findBySeq(userId).map(UserDTO::create);
    }

    @Transactional
    public UserDTO updateAll(JwtAuthentication authentication, ChangePasswordRequest request) {
        if (request.isNotRightNewPw()) {
            throw  new UnauthorizedException(request.getNewPassword()+ ", "+ request.getNewPasswordCheck() +" is Not Same.");
        }

        User user = findByEmail(authentication.email)
                .orElseThrow(() -> new NotFoundException(User.class, authentication.email));

        log.info(user.getPassword());
        log.info(request.getOriginPassword());
        log.info(passwordEncoder.encode(request.getOriginPassword()));
        if (!passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(request.getOriginPassword()))) {
            throw new UnauthorizedException("origin password is not " + request.getOriginPassword());
        }

        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
        user.updatePassword(request);
        return UserDTO.create(save(user));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        checkNotNull(email, "email must be provided.");

        return userRepository.findByEmail(email);
    }


    private User save(User user) {
        return userRepository.save(user);
    }
}
