package com.sasajankovic.domain.entities.user;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@AllArgsConstructor
@Getter
public class User implements UserDetails {
    private Long id;
    private FirstName firstName;
    private LastName lastName;
    private Username username;
    private Email email;
    private Password password;
    private UserRole role;
    private boolean enabled;
    private LocalDateTime createdAt;

    public static User createNewUser(
            FirstName firstName,
            LastName lastName,
            Email email,
            Username username,
            Password password) {
        return new User(
                null,
                firstName,
                lastName,
                username,
                email,
                password,
                UserRole.REGULAR_USER,
                false,
                LocalDateTime.now());
    }

    public void activate() {
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority(
                        FlightAdvisorConstants.ROLE_PREFIX.concat(role.toString())));
    }

    @Override
    public String getPassword() {
        return password.toString();
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
