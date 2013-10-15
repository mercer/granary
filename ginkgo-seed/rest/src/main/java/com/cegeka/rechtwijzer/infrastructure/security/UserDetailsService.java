package com.cegeka.rechtwijzer.infrastructure.security;


import com.cegeka.rechtwijzer.application.facade.UserFacade;
import com.cegeka.rechtwijzer.infrastructure.facade.UserTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserTO user = userFacade.findByEmail(email);
        return CustomUserDetails.createFrom(user);
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
