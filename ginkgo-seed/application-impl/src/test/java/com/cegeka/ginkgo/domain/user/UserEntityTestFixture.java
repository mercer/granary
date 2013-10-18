package com.cegeka.ginkgo.domain.user;

import com.cegeka.ginkgo.application.Role;
import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserToMapper;

import java.util.UUID;

public class UserEntityTestFixture {
    public static final String ID = UUID.randomUUID().toString();
    public static final String EMAIL = "test@mailinator.com";
    public static final Role ROLE = Role.USER;
    public static final String PASSWORD = "testPassword";

    private UserEntityTestFixture() {
    }

    public static UserEntity aUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(ID);
        entity.setPassword(PASSWORD);
        entity.setEmail(EMAIL);
        entity.addRole(ROLE);
        entity.setConfirmed(true);
        return entity;
    }

    public static UserEntity aUserEntityWithExtraRole(Role role) {
        UserEntity entity = aUserEntity();
        entity.addRole(role);
        return entity;
    }

    public static UserTo asUserTO() {
        return UserToMapper.toTo(aUserEntity());
    }
}
