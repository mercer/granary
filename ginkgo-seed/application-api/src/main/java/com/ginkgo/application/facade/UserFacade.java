package com.ginkgo.application.facade;

public interface UserFacade {
    void registerUser(UserTo user);

    UserTo findByEmail(String email);

    UserProfileTo getProfile(Long userId);
}
