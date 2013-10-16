package com.cegeka.ginkgo.infrastructure.security;


import com.ginkgo.application.facade.UserFacade;
import com.ginkgo.application.facade.UserTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserTo user = userFacade.findByEmail(email);
        return CustomUserDetails.createFrom(user);
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
