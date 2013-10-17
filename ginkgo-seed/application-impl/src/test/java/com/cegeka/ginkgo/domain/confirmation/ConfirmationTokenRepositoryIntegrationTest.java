package com.cegeka.ginkgo.domain.confirmation;

import com.cegeka.ginkgo.IntegrationTest;
import com.cegeka.ginkgo.domain.users.UserEntity;
import com.cegeka.ginkgo.domain.users.UserRepository;
import org.fest.assertions.Assertions;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.aUserEntity;

@Transactional
public class ConfirmationTokenRepositoryIntegrationTest extends IntegrationTest {

    UserEntity userEntity;
    ConfirmationToken confirmationToken;
    @Resource
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Resource
    private UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        //Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        //Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers").start();

        userEntity = aUserEntity();
        userEntity = userRepository.saveAndFlush(userEntity);

        confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(userEntity);
        confirmationToken.setToken(TokenFactory.generateToken());
    }

    @Test
    public void iCanPersistAConfirmationToken() {
        confirmationTokenRepository.saveAndFlush(confirmationToken);

        Assertions.assertThat(confirmationTokenRepository.findOne(confirmationToken.getToken())).isNotNull();
    }

    @Test
    public void iCanFindATokenGivenItsUser() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(userEntity);
        confirmationToken.setToken(TokenFactory.generateToken());
        confirmationTokenRepository.saveAndFlush(confirmationToken);

        Assertions.assertThat(confirmationTokenRepository.findByUser(userEntity)).isNotNull();
    }

    @After
    public void tearDown() {

    }
}