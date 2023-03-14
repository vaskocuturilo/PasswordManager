package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.PasswordModel;
import com.example.passwordmanager.entity.PasswordEntity;
import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.PasswordNotFoundException;
import com.example.passwordmanager.exceptions.UserNotActive;
import com.example.passwordmanager.repositories.PasswordManagerRepository;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordManagerService {

    private final PasswordManagerRepository passwordManagerRepository;

    private final UserRepository userRepository;

    public PasswordManagerService(PasswordManagerRepository passwordManagerRepository, UserRepository userRepository) {
        this.passwordManagerRepository = passwordManagerRepository;
        this.userRepository = userRepository;
    }

    public Iterable<PasswordModel> getAllPasswords() {
        List<PasswordModel> passwordModels = new ArrayList<>();
        Iterable<PasswordEntity> passwordEntities = passwordManagerRepository.findAll();

        for (PasswordEntity passwordEntity : passwordEntities) {
            passwordModels.add(PasswordModel.toListModel(passwordEntity));
        }
        return passwordModels;
    }

    public PasswordModel getPasswordByName(final String name) throws PasswordNotFoundException {
        Optional<PasswordEntity> existPasswordEntity = passwordManagerRepository.findByName(name);
        existPasswordEntity
                .stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> {
                    PasswordNotFoundException passwordNotFoundException = new PasswordNotFoundException("The user with id = " + name + " was not found");
                    return passwordNotFoundException;
                });
        return PasswordModel.toModel(passwordManagerRepository.findByName(name).get());
    }

    public PasswordEntity create(PasswordEntity passwordEntity, UUID userId) throws UserNotActive {
        UserEntity userEntity = userRepository.findById(userId).get();
        if (!userEntity.getActive()) {
            throw new UserNotActive("The user with userId = " + userEntity.getId() + " is not active. Please confirm user with one time password.");
        }
        passwordEntity.setUser(userEntity);
        return passwordManagerRepository.save(passwordEntity);
    }

    public PasswordEntity updatePassword(PasswordEntity entity) {
        PasswordEntity exist = passwordManagerRepository.findById(entity.getId()).get();
        exist.setName(entity.getName());
        exist.setPassword(entity.getPassword());

        return passwordManagerRepository.save(entity);

    }

    public void deletePassword(UUID id) throws PasswordNotFoundException {
        PasswordEntity existPasswordEntity = passwordManagerRepository.findById(id).get();
        if (existPasswordEntity == null) {
            throw new PasswordNotFoundException("The password with id = " + id + "was not found");
        }
        passwordManagerRepository.deleteById(id);
    }
}
