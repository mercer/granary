package com.cegeka.ginkgo.application;


import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cegeka.ginkgo.application.UserRolesConstants.ADMIN_ROLE;

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
        return UserToMapper.toToWithPassword(userRepository.findByEmail(email));
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

    @Override
//    @PreAuthorize("hasAuthority('"+ADMIN_ROLE+"')")
    @Secured(ADMIN_ROLE)
    public List<UserTo> getUsers() {
        return UserToMapper.from(userRepository.findAll());
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
