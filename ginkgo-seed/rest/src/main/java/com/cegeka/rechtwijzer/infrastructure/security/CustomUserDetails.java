package com.cegeka.rechtwijzer.infrastructure.security;

import com.cegeka.rechtwijzer.infrastructure.facade.UserTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String password;
    private String username;
    private boolean enabled;
    private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    private Locale locale;

    private CustomUserDetails() {
    }

    public static CustomUserDetails createFrom(UserTO userTo) {
        CustomUserDetails details = new CustomUserDetails();
        details.password = userTo.getPassword();
        details.userId = userTo.getId();
        details.username = userTo.getEmail();
        details.enabled = userTo.getConfirmed();
        details.locale = userTo.getLocale();

        for (String role : userTo.getRoles()) {
            details.authorities.add(new SimpleGrantedAuthority(role));
        }

        return details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
