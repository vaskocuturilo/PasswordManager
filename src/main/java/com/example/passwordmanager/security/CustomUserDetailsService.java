package com.example.passwordmanager.security;

import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity existUser = userRepository.findByUsername(username);
        if (existUser == null) {
            throw new UsernameNotFoundException("The user with email = " + username + "Not found");
        }

        return new CustomUserDetails(existUser);
    }
}
