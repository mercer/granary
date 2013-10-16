package com.cegeka.ginkgo.domain.users;


import com.ginkgo.application.facade.UserProfileTo;

public class UserProfileMapper {
    private UserProfileMapper() {
    }

    public static UserProfileTo fromUserProfileEntity(UserProfileEntity userProfileEntity) {
        UserProfileTo userProfileTo = new UserProfileTo();
        userProfileTo.setFirstName(userProfileEntity.getFirstName());
        userProfileTo.setLastName(userProfileEntity.getLastName());
        userProfileTo.setPictureUrl(userProfileEntity.getPictureUrl());
        return userProfileTo;
    }
}
