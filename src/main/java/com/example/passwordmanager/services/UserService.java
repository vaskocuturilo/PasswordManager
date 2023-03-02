package com.example.passwordmanager.services;

import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExist("User already exist, please change the username");
        }
        return userRepository.save(user);
    }

    public UserEntity getOneUser(final UUID id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
