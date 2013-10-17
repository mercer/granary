package com.cegeka.ginkgo.application;

import java.util.List;

public interface UserFacade {
    void registerUser(UserTo user);

    UserTo findByEmail(String email);

    UserProfileTo getProfile(Long userId);
    void confirmToken(String token);


    List<UserTo> getUsers();
}
