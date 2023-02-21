package com.example.passwordmanager.services;

import com.example.passwordmanager.domain.PasswordEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.passwordmanager.repositories.PasswordManagerRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordManagerService {

    private final PasswordManagerRepository passwordManagerRepository;

    public PasswordManagerService(PasswordManagerRepository passwordManagerRepository) {
        this.passwordManagerRepository = passwordManagerRepository;
    }

    public List<PasswordEntity> getAllPasswords() {
        return this.passwordManagerRepository.findAll();
    }

    public String getPasswordByName(final String name) {
        Optional<PasswordEntity> passwordEntity = passwordManagerRepository.findByName(name);

        if (passwordEntity.isPresent()) {
            return passwordEntity.get().toString();
        }
        return null;
    }

    public ResponseEntity<?> create(PasswordEntity passwordEntity) {

        PasswordEntity save = passwordManagerRepository.save(passwordEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(passwordEntity.getId()).toUri();

        return ResponseEntity.created(location).body(save);
    }
}
