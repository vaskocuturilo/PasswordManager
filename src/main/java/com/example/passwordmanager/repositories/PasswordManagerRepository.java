package com.example.passwordmanager.repositories;

import com.example.passwordmanager.domain.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordManagerRepository extends JpaRepository<PasswordEntity, UUID> {
    Optional<PasswordEntity> findByName(String name);
}
