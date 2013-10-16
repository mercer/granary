package com.cegeka.ginkgo.domain.confirmation;

import com.cegeka.ginkgo.domain.DelegatingRepository;
import com.cegeka.ginkgo.domain.users.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class ConfirmationTokenRepository  extends DelegatingRepository<ConfirmationToken, String> {

    public ConfirmationToken findByUser(UserEntity userEntity) {
        ConfirmationToken confirmationToken = null;
        try {
            confirmationToken = entityManager.createQuery("select ct from ConfirmationToken ct " +
                    "where ct.user = :user ", ConfirmationToken.class)
                    .setParameter("user", userEntity)
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return confirmationToken;
    }
}
