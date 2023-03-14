package com.example.passwordmanager.domain;

import com.example.passwordmanager.entity.PasswordEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class PasswordModel {
    private UUID id;
    private String name;
    private String password;

    public static PasswordModel toModel(PasswordEntity passwordEntity) {
        PasswordModel passwordModel = new PasswordModel();
        passwordModel.setId(passwordEntity.getId());
        passwordModel.setName(passwordEntity.getName());
        passwordModel.setPassword(passwordEntity.getPassword());

        return passwordModel;
    }

    public static PasswordModel toListModel(PasswordEntity passwordEntity) {
        PasswordModel passwordModel = new PasswordModel();
        passwordModel.setId(passwordEntity.getId());
        passwordModel.setName(passwordEntity.getName());

        return passwordModel;
    }
}
