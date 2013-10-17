package com.cegeka.ginkgo.application;

import com.cegeka.ginkgo.domain.confirmation.ConfirmationService;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRepository;
import com.cegeka.ginkgo.domain.users.UserRoleRepository;
import com.google.common.collect.Lists;
import org.fest.assertions.Assertions;
import org.fest.assertions.ListAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeImplTest {

    private UserFacadeImpl userFacade = new UserFacadeImpl();
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ConfirmationService confirmationServiceMock;
    @Mock
    private UserRoleRepository userRoleRepositoryMock;

    @Before
    public void setUp() {
        userFacade.setUserRepository(userRepositoryMock);
        userFacade.setConfirmationService(confirmationServiceMock);
        userFacade.setUserRoleRepository(userRoleRepositoryMock);
    }

    @Test
    public void testIfFindByEmailCallsUserRepositoryFindByEmail() {
        when(userRepositoryMock.findByEmail(TEST_USER_EMAIL)).thenReturn(aUserEntity());

        UserTo user = userFacade.findByEmail(TEST_USER_EMAIL);

        verify(userRepositoryMock).findByEmail(TEST_USER_EMAIL);
    }

    @Test
    public void testIfRegisterUserCallsUserRepositorySaveAndFlush_And_ConfirmationService() {
        UserTo userTO = asUserTO();
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);

        userFacade.registerUser(userTO);

        verify(userRepositoryMock).saveAndFlush(argumentCaptor.capture());
        UserEntity userEntity = argumentCaptor.getValue();
        assertThat(userEntity.getEmail()).isEqualTo(userTO.getEmail());

        verify(confirmationServiceMock).sendConfirmationEmailTo(argumentCaptor.capture());
        UserEntity userEntity2 = argumentCaptor.getValue();
        assertThat(userEntity2.getEmail()).isEqualTo(userEntity2.getEmail());
    }

    @Test
    public void givenTwoUsersWereAdded_thenRepositoryFindAllIsCalled(){
        when(userRepositoryMock.findAll()).thenReturn(Lists.newArrayList(aUserEntity(),aUserEntity()));

        List<UserTo> users = userFacade.getUsers();

        verify(userRepositoryMock).findAll();
        assertThat(users).hasSize(2);
    }
}
