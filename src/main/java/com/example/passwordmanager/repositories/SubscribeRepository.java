package com.example.passwordmanager.repositories;

import com.example.passwordmanager.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    SubscribeEntity findByEmail(final String email);
}
