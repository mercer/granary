package com.cegeka.ginkgo.domain.users;

import com.cegeka.ginkgo.domain.DelegatingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.UUID;

@Repository
public class UserRoleRepository extends DelegatingRepository<UserRoleEntity, String> {

    public UserRoleEntity findByRoleName(String roleName) {
        UserRoleEntity role = null;
        try {
            role = entityManager.createQuery("select r from UserRoleEntity r where r.name = :roleName", UserRoleEntity.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return role;
    }

}
