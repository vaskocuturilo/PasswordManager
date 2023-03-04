package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.UserModel;
import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, OneTimePasswordService oneTimePasswordService) {
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

    public UserModel getOneUser(final UUID id) {
        return UserModel.toUserModel(userRepository.findById(id).get());
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public UserModel activeUser(UUID id) {
        UserEntity existUser = userRepository.findById(id).get();
        existUser.setActive(!existUser.getActive());
        return UserModel.toUserModel(userRepository.save(existUser));
    }
}
