package com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User not found with username:" + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), assignTempRole());
    }

    private Collection<? extends GrantedAuthority> assignTempRole() {
        return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
    }
}
