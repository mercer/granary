package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.application.UserTo;

public class UserToMapper {
    private UserToMapper() {
    }

    public static UserTo toTo(UserEntity userEntity) {
        UserTo userTo = new UserTo();
        userTo.setId(userEntity.getId());
        userTo.setEmail(userEntity.getEmail());
        userTo.setPassword(userEntity.getPassword());
        userTo.setRoles(userEntity.getRoles());
        userTo.setConfirmed(userEntity.isConfirmed());
        userTo.setFirstName(userEntity.getProfile().getFirstName());
        userTo.setLastName(userEntity.getProfile().getLastName());
        userTo.setLocale(userEntity.getLocale());
        return userTo;
    }

    public static UserEntity toEntity(UserTo userTo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userTo.getEmail());
        userEntity.setPassword(userTo.getPassword());
        userEntity.getProfile().setFirstName(userTo.getFirstName());
        userEntity.getProfile().setLastName(userTo.getLastName());
        return userEntity;
    }
}
