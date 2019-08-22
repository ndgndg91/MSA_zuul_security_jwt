package com.ndgndg91.security.authserver.repository.user;

import com.ndgndg91.security.authserver.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySeq(Long userSeq);

    Optional<User> findByEmail(String email);

}
