package com.example.passwordmanager.domain;

import com.example.passwordmanager.entity.OneTimePasswordEntity;
import com.example.passwordmanager.entity.UserEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserModel {

    private UUID id;
    private String username;
    private Boolean active;
    List<PasswordModel> passwordEntityList;
    private OneTimePasswordEntity oneTimePassword;

    public static UserModel toFullUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setActive(userEntity.getActive());
        userModel.setPasswordEntityList(userEntity.getPasswordEntities()
                .stream()
                .map(PasswordModel::toModel)
                .collect(Collectors.toList()));
        userModel.setOneTimePassword(userEntity.getOneTimePasswordEntity());

        return userModel;
    }

    public static UserModel toUserModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setActive(userEntity.getActive());
        userModel.setOneTimePassword(userEntity.getOneTimePasswordEntity());

        return userModel;
    }

    public static UserModel toListModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setActive(userEntity.getActive());

        return userModel;
    }
}
