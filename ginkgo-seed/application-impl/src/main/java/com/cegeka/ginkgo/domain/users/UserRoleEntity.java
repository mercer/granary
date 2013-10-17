package com.cegeka.ginkgo.domain.users;

import javax.persistence.*;

@Entity
@Table(name="ROLES")
@SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq_gen")
public class UserRoleEntity {
    @Id
    @GeneratedValue(generator = "role_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    public UserRoleEntity(String roleName) {
        this.name = roleName;
    }

    public UserRoleEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
