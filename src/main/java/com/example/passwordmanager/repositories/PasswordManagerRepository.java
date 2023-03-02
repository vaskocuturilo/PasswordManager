package com.example.passwordmanager.repositories;

import com.example.passwordmanager.domain.PasswordEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordManagerRepository extends CrudRepository<PasswordEntity, Long> {
    Optional<PasswordEntity> findByName(String name);
}
