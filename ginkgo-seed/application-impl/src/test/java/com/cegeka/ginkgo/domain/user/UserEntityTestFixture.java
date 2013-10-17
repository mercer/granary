package com.cegeka.ginkgo.domain.user;

import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRoleEntity;
import com.cegeka.ginkgo.domain.users.UserToMapper;

import java.util.UUID;

public class UserEntityTestFixture {
    public static final String TEST_USER_ENTITY_ID = UUID.randomUUID().toString();
    public static final String TEST_USER_EMAIL = "test@mailinator.com";
    public static final String TEST_ROLE = "testRole";

    private UserEntityTestFixture() {
    }

    public static UserEntity aUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(TEST_USER_ENTITY_ID);
        entity.setPassword("testPassword");
        entity.setEmail(TEST_USER_EMAIL);
        entity.addRole(new UserRoleEntity(TEST_ROLE));
        return entity;
    }

    public static UserTo asUserTO() {
        return UserToMapper.toTo(aUserEntity());
    }
}
