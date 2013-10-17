package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.domain.user.UserEntityTestFixture;
import org.fest.assertions.Assertions;
import org.junit.Test;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.TEST_PASSWORD;
import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.aUserEntity;

public class UserToMapperTest {

    @Test
    public void mapperShouldNotCopyPasswordToTo() {
        UserEntity userEntity = aUserEntity();
        userEntity.setPassword(TEST_PASSWORD);

        UserTo userTo = UserToMapper.toTo(userEntity);

        Assertions.assertThat(userTo.getPassword()).isNull();
    }
}
