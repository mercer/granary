package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.application.UserTo;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

public class UserToMapper {
    private UserToMapper() {
    }

    public static UserTo toTo(UserEntity userEntity) {
        UserTo userTo = new UserTo();
        userTo.setId(userEntity.getId());
        userTo.setEmail(userEntity.getEmail());
        //userTo.setPassword(userEntity.getPassword());
        userTo.setRoles(userEntity.getRoles());
        userTo.setConfirmed(userEntity.isConfirmed());
        userTo.setFirstName(userEntity.getProfile().getFirstName());
        userTo.setLastName(userEntity.getProfile().getLastName());
        userTo.setLocale(userEntity.getLocale());
        return userTo;
    }

    public static UserTo toToWithPassword(UserEntity userEntity) {
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

    public static List<UserTo> from(List<UserEntity> all) {
        return newArrayList(transform(all, userEntityToUserToTransform()));
    }

    private static Function<UserEntity, UserTo> userEntityToUserToTransform() {
        return new Function<UserEntity, UserTo>() {
            @Override
            public UserTo apply(@Nullable UserEntity input) {
                return toTo(input);
            }
        };
    }
}
