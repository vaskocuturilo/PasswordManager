package com.example.passwordmanager.repositories;

import com.example.passwordmanager.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByUsername(final String username);
}
