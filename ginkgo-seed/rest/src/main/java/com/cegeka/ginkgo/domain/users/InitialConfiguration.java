package com.cegeka.ginkgo.domain.users;

import com.ginkgo.application.facade.UserProfileTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class InitialConfiguration {
    public static final String TEST_USER_PASSWORD = "test";
    public static final String USER_ROLE_NAME = "user";
    private static String[] emails = {"romeo@mailinator.com", "juliet@mailinator.com"};
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Autowired
    private org.springframework.context.ApplicationContext applicationContext;

    @PostConstruct
    public void configure() {
        InitialConfiguration proxyBean = applicationContext.getBean(this.getClass());
        proxyBean.createTestUsersIfNeeded();
    }

    public void createTestUsersIfNeeded() {
        for (String email : emails) {
            if (!userExists(email)) {
                createUserAndRoles(email);
            }
        }
    }



    private void createUserAndRoles(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        setProfileForEmail(email, userEntity);
        userEntity.setPassword(TEST_USER_PASSWORD);
        userEntity.addRole(createOrFindRole(USER_ROLE_NAME));
        userEntity.setConfirmed(true);
        userRepository.saveAndFlush(userEntity);
    }

    private void setProfileForEmail(String email, UserEntity userEntity) {
        UserProfileTo userProfileTo = email.contains(emails[0]) ? getRomeoProfile(): getJulietProfile();
        userEntity.getProfile().setFirstName(userProfileTo.getFirstName());
        userEntity.getProfile().setLastName(userProfileTo.getLastName());
        userEntity.getProfile().setPictureUrl(userProfileTo.getPictureUrl());
    }

    private UserRoleEntity createOrFindRole(String roleName) {
        UserRoleEntity role = userRoleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new UserRoleEntity(roleName);
            role = userRoleRepository.saveAndFlush(role);
        }
        return role;
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    private UserProfileTo getJulietProfile() {
        UserProfileTo julietProfile = new UserProfileTo();
        julietProfile.setFirstName("Juliet");
        julietProfile.setLastName("Zeldenthuis");
        julietProfile.setPictureUrl("juliet");

        return julietProfile;
    }

    private UserProfileTo getRomeoProfile() {
        UserProfileTo romeoProfile = new UserProfileTo();
        romeoProfile.setFirstName("Romeo");
        romeoProfile.setLastName("Zeldenthuis");
        romeoProfile.setPictureUrl("romeo");
        return romeoProfile;
    }
}
