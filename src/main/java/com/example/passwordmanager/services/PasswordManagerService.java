package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.PasswordModel;
import com.example.passwordmanager.entity.PasswordEntity;
import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import com.example.passwordmanager.repositories.PasswordManagerRepository;

import java.util.UUID;

@Service
public class PasswordManagerService {

    private final PasswordManagerRepository passwordManagerRepository;

    private final UserRepository userRepository;

    public PasswordManagerService(PasswordManagerRepository passwordManagerRepository, UserRepository userRepository) {
        this.passwordManagerRepository = passwordManagerRepository;
        this.userRepository = userRepository;
    }

    public Iterable<PasswordEntity> getAllPasswords() {

        return passwordManagerRepository.findAll();
    }

    public PasswordModel getPasswordByName(final String name) {
        return PasswordModel.toModel(passwordManagerRepository.findByName(name).get());
    }

    public PasswordEntity create(PasswordEntity passwordEntity, UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        passwordEntity.setUser(userEntity);
        return passwordManagerRepository.save(passwordEntity);
    }

    public PasswordEntity updatePassword(PasswordEntity entity) {
        PasswordEntity exist = passwordManagerRepository.findById(entity.getId()).get();
        exist.setName(entity.getName());
        exist.setPassword(entity.getPassword());

        return passwordManagerRepository.save(entity);

    }

    public void deletePassword(UUID id) {
        passwordManagerRepository.deleteById(id);
    }
}
