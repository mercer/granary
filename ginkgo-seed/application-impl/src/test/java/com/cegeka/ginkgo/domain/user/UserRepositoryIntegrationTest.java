package com.cegeka.ginkgo.domain.user;

import com.cegeka.ginkgo.IntegrationTest;
import com.cegeka.ginkgo.application.Role;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRepository;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.aUserEntity;
import static org.fest.assertions.Assertions.assertThat;

public class UserRepositoryIntegrationTest extends IntegrationTest {

    public static final String PICTURE_URL = "pictureUrl";
    public static final String TEST_USER_EMAIL_2 = "some_email@email.com";
    @Resource
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.saveAndFlush(aUserEntity());
    }

    @Test(expected = PersistenceException.class)
    public void emailIsUnique() {
        userRepository.saveAndFlush(aUserEntity());
    }

    @Test
    public void testFindByEmail() {
        UserEntity user = userRepository.findByEmail(UserEntityTestFixture.EMAIL);
        assertThat(user.getEmail()).isEqualTo(UserEntityTestFixture.EMAIL);
    }

    @Test
    public void testCanAssignAndRetrieve_a_UserProfile() {
        UserEntity userEntity = aUserEntity();
        userEntity.setEmail(TEST_USER_EMAIL_2);
        userEntity.getProfile().setPictureUrl(PICTURE_URL);

        userRepository.saveAndFlush(userEntity);
        UserEntity retrievedUser = userRepository.findByEmail(TEST_USER_EMAIL_2);

        assertThat(retrievedUser.getProfile().getPictureUrl()).isEqualTo(PICTURE_URL);
    }


    @Test
    public void changingRolesForAUserTest() {
        UserEntity aUser = userRepository.findByEmail(UserEntityTestFixture.EMAIL);
        aUser.getRoles().remove(Role.USER);
        aUser.getRoles().add(Role.ADMIN);

        userRepository.saveAndFlush(aUser);
        UserEntity savedUser = userRepository.findOne(aUser.getId());

        Assertions.assertThat(savedUser.getRoles()).containsOnly(Role.ADMIN);
    }
}
