package com.cegeka.ginkgo.application;

public interface UserFacade {
    void registerUser(UserTo user);

    UserTo findByEmail(String email);

    UserProfileTo getProfile(String userId);

    void confirmToken(String token);


}
