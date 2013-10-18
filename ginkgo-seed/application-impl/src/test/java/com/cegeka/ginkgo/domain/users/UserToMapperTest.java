package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.domain.user.UserEntityTestFixture;
import org.junit.Test;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.aUserEntity;
import static org.fest.assertions.Assertions.assertThat;

public class UserToMapperTest {

    @Test
    public void toToCorrectlyMapsFields() {
        UserEntity expected = aUserEntity();

        UserTo actual = UserToMapper.toTo(expected);

        assertMappedFields(expected, actual);
        assertThat(actual.getPassword()).isNull();
    }

    @Test
    public void mapperShouldCopyPasswordTo_whenToToWithPasswordIsCalled() {
        UserEntity expected = aUserEntity();

        UserTo actual = UserToMapper.toToWithPassword(expected);

        assertMappedFields(expected, actual);
        assertThat(actual.getPassword()).isEqualTo(UserEntityTestFixture.PASSWORD);
    }

    private void assertMappedFields(UserEntity expected, UserTo actual) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getConfirmed()).isEqualTo(expected.isConfirmed());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(expected.getProfile().getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getProfile().getLastName());
        assertThat(actual.getLocale()).isEqualTo(expected.getLocale());
        assertThat(actual.getRoles()).containsOnly(expected.getRoles().toArray());
    }

}
