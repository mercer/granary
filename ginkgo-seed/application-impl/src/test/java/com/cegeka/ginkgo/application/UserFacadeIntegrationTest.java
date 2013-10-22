package com.cegeka.ginkgo.application;

import com.cegeka.ginkgo.IntegrationTest;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRepository;
import com.cegeka.ginkgo.domain.users.UserToMapper;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.List;

import static com.cegeka.ginkgo.application.UserToTestFixture.*;
import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.*;

public class UserFacadeIntegrationTest extends IntegrationTest {

    @Resource
    private UserFacade userFacade;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserToMapper userToMapper;

    @Test
    public void givenAUserWithAdminRole_thenGetUsersReturnsAListOfUsers() {
        UserEntity user = userRepository.saveAndFlush(aUserEntityWithExtraRole(Role.ADMIN));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
        List<UserTo> users = userFacade.getUsers();
        Assertions.assertThat(users).containsOnly(userToMapper.toTo(user));
    }

    @Test(expected = AccessDeniedException.class)
    public void roleAdminIsNeededForListingAllUsers() {
        userRepository.saveAndFlush(aUserEntity());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
        userFacade.getUsers();
    }

    @Test(expected = AccessDeniedException.class)
    public void regularUsersShouldNotBeAbleToCreateNewUsers(){
        UserEntity normalUser = userRepository.saveAndFlush(aUserEntityWithExtraRole(Role.USER));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
        userFacade.createNewUser(new UserTo());
    }
    @Test
    public void adminsShouldBeAbleToCreateNewUsers(){
        UserEntity normalUser = userRepository.saveAndFlush(aUserEntityWithExtraRole(Role.ADMIN));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));
        UserTo userTo = aUserTo();

        Assertions.assertThat(userRepository.findByEmail(userTo.getEmail())).isNull();
        userFacade.createNewUser(userTo);
        Assertions.assertThat(userRepository.findByEmail(userTo.getEmail())).isNotNull();
    }

    @Test
    public void regularUsersShouldOnlyEditTheirOwnAccount(){
        UserEntity normalUser = userRepository.saveAndFlush(aUserEntityWithExtraRole(Role.USER));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD));

        Assertions.assertThat(normalUser.getEmail()).isEqualTo(EMAIL);

        UserTo userBeingEdited = aUserTo();
        userBeingEdited.setId(normalUser.getId());
        userFacade.updateUser(userBeingEdited);

    }


}
