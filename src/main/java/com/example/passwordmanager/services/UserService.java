package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.UserEntity;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(final UserEntity user) throws UserAlreadyExist {
        UserEntity userEntity = userRepository.findById(user.getId()).get();
        if (userEntity != null) {
            throw new UserAlreadyExist("");
        }
        return userRepository.save(user);
    }
}
