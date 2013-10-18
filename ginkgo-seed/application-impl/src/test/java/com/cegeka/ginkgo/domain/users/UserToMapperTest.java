package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.application.UserTo;
import com.cegeka.ginkgo.application.UserToTestFixture;
import com.cegeka.ginkgo.domain.user.UserEntityTestFixture;
import org.junit.Test;

import java.util.Locale;

import static com.cegeka.ginkgo.domain.user.UserEntityTestFixture.aUserEntity;
import static org.fest.assertions.Assertions.assertThat;

public class UserToMapperTest {

    @Test
    public void toToCorrectlyMapsFields() {
        UserEntity given = aUserEntity();

        UserTo actual = UserToMapper.toTo(given);

        assertMappedTo(given, actual);
        assertThat(actual.getPassword()).isNull();
    }

    @Test
    public void mapperShouldCopyPasswordTo_whenToToWithPasswordIsCalled() {
        UserEntity given = aUserEntity();

        UserTo actual = UserToMapper.toToWithPassword(given);

        assertMappedTo(given, actual);
        assertThat(actual.getPassword()).isEqualTo(UserEntityTestFixture.PASSWORD);
    }

    @Test
    public void mapToNewEntityFromToShouldMapFields() {
        UserTo given = UserToTestFixture.aUserTo();

        UserEntity actual = UserToMapper.toNewEntity(given);

        assertMappedEntity(given, actual);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getLocale()).isEqualTo(Locale.ENGLISH);
    }

    private void assertMappedEntity(UserTo given, UserEntity actual) {
        assertThat(actual.isConfirmed()).isEqualTo(given.getConfirmed());
        assertThat(actual.getEmail()).isEqualTo(given.getEmail());
        assertThat(actual.getProfile().getFirstName()).isEqualTo(given.getFirstName());
        assertThat(actual.getProfile().getLastName()).isEqualTo(given.getLastName());
        //assertThat(actual.getRoles()).containsOnly(given.getRoles().toArray()); TODO: map roles
    }

    private void assertMappedTo(UserEntity given, UserTo actual) {
        assertThat(actual.getId()).isEqualTo(given.getId());
        assertThat(actual.getConfirmed()).isEqualTo(given.isConfirmed());
        assertThat(actual.getEmail()).isEqualTo(given.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(given.getProfile().getFirstName());
        assertThat(actual.getLastName()).isEqualTo(given.getProfile().getLastName());
        assertThat(actual.getLocale()).isEqualTo(given.getLocale());
        assertThat(actual.getRoles()).containsOnly(given.getRoles().toArray());
    }

}
