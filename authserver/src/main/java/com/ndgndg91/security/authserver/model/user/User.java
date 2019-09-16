package com.ndgndg91.security.authserver.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ndgndg91.security.authserver.model.api.request.user.ChangePasswordRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Table(name = "user")
@Entity
public class User {
    private static final String PROTECTED = "[PROTECTED]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "seq")final Long seq;

    private @Column(name = "name") final String name;

    private @Column(name = "email") final String email;

    @JsonIgnore
    private @Column(name = "password") String password;

    private @Column(name = "loginCount") int loginCount;

    private @Column(name = "lastLoginAt") LocalDateTime lastLoginAt;

    private @Column(name = "createAt") final LocalDateTime createAt;

    private User(){
        seq = null;
        name = null;
        email = null;
        createAt = null;
    };

    public User(String name, String email, String password) {
        this(null, name, email, password, 0, null, null);
    }

    public User(Long seq, String name, String email, String password, int loginCount, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
                name.length() >= 1 && name.length() <= 10,
                "name length must be between 1 and 10 characters."
        );
        checkNotNull(email, "email must be provided.");
        checkArgument(isNotEmpty(email), "address must be provided.");
        checkArgument(
            email.length() >= 4 && email.length() <= 50,
            "address length must be between 4 and 50 characters."
        );
        checkArgument(checkAddress(email), "Invalid email address: " + email);

        checkNotNull(password, "password must be provided.");

        this.seq = seq;
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
        this.createAt = defaultIfNull(createAt, now());
    }

    private static boolean checkAddress(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password))
            throw new IllegalArgumentException("Bad credential");
    }

    public void updatePassword(ChangePasswordRequest request) {
        this.password = request.getNewPassword();
    }

    public void afterLoginSuccess() {
        loginCount++;
        lastLoginAt = now();
    }

    public Long getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public Optional<LocalDateTime> getLastLoginAt() {
        return ofNullable(lastLoginAt);
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(seq, user.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("name", name)
                .append("email", email)
                .append("password", PROTECTED)
                .append("loginCount", loginCount)
                .append("lastLoginAt", lastLoginAt)
                .append("createAt", createAt)
                .toString();
    }

    static public class Builder {
        private Long seq;
        private String name;
        private String email;
        private String password;
        private int loginCount;
        private LocalDateTime lastLoginAt;
        private LocalDateTime createAt;

        public Builder() {}

        public Builder(User user) {
            this.seq = user.seq;
            this.name = user.name;
            this.email = user.email;
            this.password = user.password;
            this.loginCount = user.loginCount;
            this.lastLoginAt = user.lastLoginAt;
            this.createAt = user.createAt;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder loginCount(int loginCount) {
            this.loginCount = loginCount;
            return this;
        }

        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }

        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public User build() {
            return new User(seq, name, email, password, loginCount, lastLoginAt, createAt);
        }
    }

}
