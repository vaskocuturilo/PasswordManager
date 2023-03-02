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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExist("User already exist, please change the username");
        }
        return userRepository.save(user);
    }

    public UserEntity getOneUser(final Long id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
