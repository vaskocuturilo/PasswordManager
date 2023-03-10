package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.UserModel;
import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.OneTimePasswordErrorException;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.repositories.OneTimePasswordRepository;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final OneTimePasswordService oneTimePasswordService;

    public UserService(UserRepository userRepository,
                       OneTimePasswordRepository oneTimePasswordRepository, OneTimePasswordService oneTimePasswordService) {
        this.userRepository = userRepository;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(final UserEntity user) throws UserAlreadyExist {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExist("User already exist, please change the username");
        }

        UserEntity saveUser = userRepository.save(user);
        oneTimePasswordService.returnOneTimePassword(saveUser.getId());

        return saveUser;
    }

    public UserModel getOneUser(final UUID id) {
        return UserModel.toFullUserModel(userRepository.findById(id).get());
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public UserModel activeUser(final UUID id, final Integer oneTimePasswordCode) throws OneTimePasswordErrorException {
        UserEntity existUser = userRepository.findById(id).get();
        Integer oneTimePasswordCodeExist = oneTimePasswordRepository.findByOneTimePasswordCode(id);
        if (!oneTimePasswordCodeExist.equals(oneTimePasswordCode)) {
            throw new OneTimePasswordErrorException("The one time password code = " + oneTimePasswordCode + " not correctly. PLease, check you email.");
        }
        existUser.setActive(!existUser.getActive());
        return UserModel.toFullUserModel(userRepository.save(existUser));
    }
}
