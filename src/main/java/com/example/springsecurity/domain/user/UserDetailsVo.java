package com.example.springsecurity.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsVo implements UserDetails {

    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }
}
