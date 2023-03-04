package com.example.passwordmanager.repositories;

import com.example.passwordmanager.entity.OneTimePasswordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OneTimePasswordRepository extends CrudRepository<OneTimePasswordEntity, UUID> {

}
