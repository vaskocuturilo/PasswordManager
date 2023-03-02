package com.example.passwordmanager.repositories;

import com.example.passwordmanager.entity.PasswordEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordManagerRepository extends CrudRepository<PasswordEntity, UUID> {
    Optional<PasswordEntity> findByName(String name);
}
