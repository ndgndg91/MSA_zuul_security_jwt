package com.ndgndg91.security.authserver.service.user;

import com.ndgndg91.security.authserver.error.NotFoundException;
import com.ndgndg91.security.authserver.model.user.User;
import com.ndgndg91.security.authserver.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User join(String name, String email, String password) {
        checkArgument(isNotEmpty(password), "password must be provided.");
        checkArgument(
                password.length() >= 4 && password.length() <= 15,
                "password length must be between 4 and 15 characters."
        );

        User user = new User(name, email, passwordEncoder.encode(password));
        return save(user);
    }

    @Transactional
    public User login(String email, String password) {
        checkNotNull(password, "password must be provided.");

        User user = findByEmail(email)
                .orElseThrow(() -> new NotFoundException(User.class, email));
        user.login(passwordEncoder, password);
        user.afterLoginSuccess();
        save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findBySeq(userId);
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
