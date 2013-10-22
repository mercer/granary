package com.cegeka.ginkgo.application;

import com.cegeka.ginkgo.application.security.CustomUserDetails;
import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserProfileMapper;
import com.cegeka.ginkgo.domain.users.UserRepository;
import com.cegeka.ginkgo.domain.users.UserToMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.cegeka.ginkgo.application.Role.USER;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationService confirmationService;
    @Resource
    private UserToMapper userToMapper;

    @Override
    public void registerUser(UserTo user) {
        UserEntity userEntity = userToMapper.toNewEntity(user);
        userEntity.addRole(USER);
        userEntity.setPassword( passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(userEntity);
        confirmationService.sendConfirmationEmailTo(userEntity);
    }

    @Override
    public UserTo findByEmail(String email) {
        return userToMapper.toToWithPassword(userRepository.findByEmail(email));
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
        return userToMapper.from(userRepository.findAll());
    }

    @Override
    public UserTo getUser(String userId) {
        return userToMapper.toTo(userRepository.findOne(userId));
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setConfirmationService(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(com.cegeka.ginkgo.application.Role).ADMIN)")
    public void updateOrCreateNewUser(UserTo userTo) {
        if (userTo.getId() == null) {
            createNewUser(userTo);
        } else {
            updateUser(userTo);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(com.cegeka.ginkgo.application.Role).ADMIN)")
    public void createNewUser(UserTo userTo) {
        UserEntity userEntity = userToMapper.toNewEntity(userTo);
        userRepository.save(userEntity);

    }

    @Override
    public void updateUser(UserTo userTo) {
        doesTheLoggedInUserHaveTheRightToEdit(userTo);
        UserEntity userEntity = userRepository.findOne(userTo.getId());
        if (userEntity != null) {
            userToMapper.toExistingEntity(userEntity, userTo);
        } else {
            throw new IllegalArgumentException("invalid user id");
        }
        userRepository.save(userEntity);
    }

    private void doesTheLoggedInUserHaveTheRightToEdit(UserTo userTo) {
        //obtain the loggedIn spring-security user
       // System.out.println("Principal: "+  SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //check that the logged in user is either an ADMIN or a regular user that has the sane id

    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setUserToMapper(UserToMapper userToMapper) {
        this.userToMapper = userToMapper;
    }
}
