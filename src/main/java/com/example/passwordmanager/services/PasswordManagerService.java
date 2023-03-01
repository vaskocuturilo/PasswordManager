package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.PasswordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.passwordmanager.repositories.PasswordManagerRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordManagerService {

    private final PasswordManagerRepository passwordManagerRepository;

    public PasswordManagerService(PasswordManagerRepository passwordManagerRepository) {
        this.passwordManagerRepository = passwordManagerRepository;
    }

    public List<PasswordEntity> getAllPasswords() {
        return passwordManagerRepository.findAll();
    }

    public PasswordEntity getPasswordByName(final String name) {
        return passwordManagerRepository.findByName(name).get();
    }

    public ResponseEntity create(PasswordEntity passwordEntity) {

        PasswordEntity save = passwordManagerRepository.save(passwordEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(passwordEntity.getId()).toUri();

        return ResponseEntity.created(location).body(save);
    }

    public PasswordEntity updatePassword(PasswordEntity entity) {
        PasswordEntity exist = passwordManagerRepository.findById(entity.getId()).get();
        exist.setName(entity.getName());
        exist.setPassword(entity.getPassword());

        passwordManagerRepository.save(entity);
        return null;
    }
}
