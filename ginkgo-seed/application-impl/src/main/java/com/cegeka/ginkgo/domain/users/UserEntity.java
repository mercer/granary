package com.cegeka.ginkgo.domain.users;

import com.google.common.collect.Sets;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Locale;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String password;
    @ManyToMany(fetch = EAGER, cascade = ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<UserRoleEntity> roles = Sets.newHashSet();
    @Column(unique = true)
    private String email;
    @Transient
    private Locale locale = Locale.ENGLISH; //TODO: who should provide this?
    private boolean confirmed;
    @OneToOne(cascade = ALL)
    @JoinColumn(referencedColumnName = "ID", nullable = false)
    private UserProfileEntity profile = new UserProfileEntity();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        Set<String> rolesNames = Sets.newHashSet();
        for (UserRoleEntity roleEntity : roles) {
            rolesNames.add(roleEntity.getName());
        }
        return rolesNames;
    }

    public void addRole(UserRoleEntity role) {
        this.roles.add(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public UserProfileEntity getProfile() {
        return profile;
    }
}

