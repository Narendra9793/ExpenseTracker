package org.authservice.services;

import org.authservice.entities.UserInfo;
import org.authservice.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




public class CustomUserDetails extends UserInfo implements UserDetails {
    private String email;
    private String password;
    Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(UserInfo user) {
        this.email=user.getEmail();
        this.password=user.getPassword();
        List<GrantedAuthority> auth= new ArrayList<>();

        for (UserRole role : user.getRoles()){
            auth.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


}
