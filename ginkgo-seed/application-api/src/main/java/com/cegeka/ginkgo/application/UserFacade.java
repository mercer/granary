package com.cegeka.ginkgo.application;

import java.util.List;

public interface UserFacade {
    void registerUser(UserTo user);

    UserTo findByEmail(String email);

    UserProfileTo getProfile(String userId);

    void confirmToken(String token);


    List<UserTo> getUsers();

    UserTo getUser(String userId);

    void updateUser(UserTo userTO);
}
