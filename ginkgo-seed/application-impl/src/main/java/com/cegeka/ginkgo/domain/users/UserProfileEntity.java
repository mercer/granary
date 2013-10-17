package com.cegeka.ginkgo.domain.users;

import javax.persistence.*;

@Entity
@Table(name = "USER_PROFILE")
@SequenceGenerator(sequenceName = "user_prof_seq", name = "user_prof_seq")
public class UserProfileEntity {
    @Id
    @GeneratedValue(generator = "user_prof_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String pictureUrl;
    private String firstName;
    private String lastName;

    public UserProfileEntity(){}

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
