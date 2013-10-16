package com.cegeka.ginkgo.application;


import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.*;
import com.cegeka.ginkgo.application.UserFacade;
import com.cegeka.ginkgo.application.UserProfileTo;
import com.cegeka.ginkgo.application.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeImpl implements UserFacade {
    public static final String USER_ROLE = "user";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationService confirmationService;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public void registerUser(UserTo user) {
        UserEntity userEntity = UserToMapper.toEntity(user);
        userEntity.addRole(userRoleRepository.findByRoleName(USER_ROLE));
        userRepository.saveAndFlush(userEntity);
        confirmationService.sendConfirmationEmailTo(userEntity);
    }

    @Override
    public UserTo findByEmail(String email) {
        return UserToMapper.toTo(userRepository.findByEmail(email));
    }

    @Override
    public UserProfileTo getProfile(Long userId) {
        UserEntity userEntity = userRepository.findOne(userId);
        return UserProfileMapper.fromUserProfileEntity(userEntity.getProfile());
    }

    @Override
    public void confirmToken(String token) {
        confirmationService.confirmToken(token);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setConfirmationService(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
}
