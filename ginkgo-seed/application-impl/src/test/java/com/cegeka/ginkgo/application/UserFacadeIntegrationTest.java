package com.cegeka.ginkgo.application;

import com.cegeka.ginkgo.IntegrationTest;
import com.cegeka.ginkgo.application.security.CustomUserDetails;
import com.cegeka.ginkgo.domain.user.UserEntityTestFixture;
import com.cegeka.ginkgo.domain.users.*;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;

import static com.cegeka.ginkgo.application.UserRolesConstants.ADMIN_ROLE;

public class UserFacadeIntegrationTest extends IntegrationTest {

    @Resource
    private UserFacade userFacade;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    private UserEntity userEntity;

    @Test
    public void givenAUserWithAdminRole_thenGetUsersReturnsAListOfUsers() {

        createUserWithRole(ADMIN_ROLE);

        UserTo userTo = UserToMapper.toToWithPassword(userEntity);

        CustomUserDetails customUserDetails = CustomUserDetails.createFrom(userTo);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails.getUsername(), customUserDetails.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        userFacade.getUsers();
    }

    @Test(expected = AccessDeniedException.class)
    public void givenAUserWithoutRole_thenGetUsersReturnsAListOfUsers() {

        createUserWithRole("OTHER_ROLE");

        UserTo userTo = UserToMapper.toToWithPassword(userEntity);

        CustomUserDetails customUserDetails = CustomUserDetails.createFrom(userTo);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails.getUsername(), customUserDetails.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        userFacade.getUsers();
    }

    private void createUserWithRole(String role) {
        userEntity = UserEntityTestFixture.aUserEntity();

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userEntity.setConfirmed(true);
        userRoleEntity.setName(role);
        userRoleRepository.saveAndFlush(userRoleEntity);

        userEntity.addRole(userRoleRepository.findByRoleName(role));
        userRepository.saveAndFlush(userEntity);
    }


}
