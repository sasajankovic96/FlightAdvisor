package com.sasajankovic.service;

import com.sasajankovic.domain.entities.user.Username;
import com.sasajankovic.domain.ports.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(Username.of(s))
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        String.format("User with username %s not found", s)));
    }
}
