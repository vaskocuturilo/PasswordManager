package com.example.passwordmanager.repositories;

import com.example.passwordmanager.entity.OneTimePasswordEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OneTimePasswordRepository extends CrudRepository<OneTimePasswordEntity, UUID> {
    @Query("SELECT code.oneTimePasswordCode FROM OneTimePasswordEntity code WHERE code.user.id = ?1")
    Integer findByOneTimePasswordCode(final UUID id);

    @Modifying
    @Query("delete from OneTimePasswordEntity code where code.oneTimePasswordCode = :code")
    void deleteByOneTimePasswordCode(final Integer code);
}
