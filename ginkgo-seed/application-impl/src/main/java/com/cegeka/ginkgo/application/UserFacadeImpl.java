package com.cegeka.ginkgo.application;

import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserProfileMapper;
import com.cegeka.ginkgo.domain.users.UserRepository;
import com.cegeka.ginkgo.domain.users.UserToMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cegeka.ginkgo.application.Role.USER;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationService confirmationService;

    @Override
    public void registerUser(UserTo user) {
        UserEntity userEntity = UserToMapper.toNewEntity(user);
        userEntity.addRole(USER);
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
    @PreAuthorize("hasRole(T(com.cegeka.ginkgo.application.Role).ADMIN)")
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
