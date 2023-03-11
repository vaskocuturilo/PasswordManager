package com.example.passwordmanager.entity;

import com.example.passwordmanager.encryption.AttributeEncryptor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.UUID;

@Entity
@Table(name = "password_entity")
@NoArgsConstructor
@Data
public class PasswordEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    @Convert(converter = AttributeEncryptor.class)
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    UserEntity user;

}
