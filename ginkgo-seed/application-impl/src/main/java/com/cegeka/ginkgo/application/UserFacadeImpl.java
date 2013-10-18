package com.cegeka.ginkgo.application;


import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cegeka.ginkgo.application.UserRolesConstants.ADMIN_ROLE;
import static com.cegeka.ginkgo.application.UserRolesConstants.USER_ROLE;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationService confirmationService;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public void registerUser(UserTo user) {
        UserEntity userEntity = UserToMapper.toNewEntity(user);
        userEntity.addRole(userRoleRepository.findByRoleName(USER_ROLE));
        userRepository.saveAndFlush(userEntity);
        confirmationService.sendConfirmationEmailTo(userEntity);
    }

    @Override
    public UserTo findByEmail(String email) {
        return UserToMapper.toToWithPassword(userRepository.findByEmail(email));
    }

    @Override
    public UserProfileTo getProfile(String userId) {
        UserEntity userEntity = userRepository.findOne(userId);
        return UserProfileMapper.fromUserProfileEntity(userEntity.getProfile());
    }

    @Override
    public void confirmToken(String token) {
        confirmationService.confirmToken(token);
    }

    @Override
    @Secured(ADMIN_ROLE)
    public List<UserTo> getUsers() {
        return UserToMapper.from(userRepository.findAll());
    }

    @Override
    public UserTo getUser(String userId) {
        return UserToMapper.toTo(userRepository.findOne(userId));
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

    @Override
    @Transactional
    public void updateUser(UserTo userTO) {
        UserEntity userEntity = null;
        if (userTO.getId() == null) {
            userEntity = UserToMapper.toNewEntity(userTO);
        } else {
            userEntity = userRepository.findOne(userTO.getId());
            if (userEntity != null) {
                userEntity.setEmail(userTO.getEmail());
                userEntity.getProfile().setFirstName(userTO.getFirstName());
                userEntity.getProfile().setLastName(userTO.getLastName());
                userEntity.setConfirmed(userTO.getConfirmed());
            } else {
                throw new IllegalArgumentException("invalid user id");
            }
        }
        userRepository.save(userEntity);

    }

}
