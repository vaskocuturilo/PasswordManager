package com.example.passwordmanager.domain;

import com.example.passwordmanager.entity.UserEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserModel {

    UUID id;
    private String username;
    List<PasswordModel> passwordEntityList;

    public static UserModel toUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setPasswordEntityList(userEntity.getPasswordEntities()
                .stream()
                .map(PasswordModel::toModel)
                .collect(Collectors.toList()));
        return userModel;
    }
}
