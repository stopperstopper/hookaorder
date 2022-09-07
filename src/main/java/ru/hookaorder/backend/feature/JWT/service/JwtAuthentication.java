package ru.hookaorder.backend.feature.JWT.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.hookaorder.backend.feature.roles.entity.ERole;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private String phone;
    private String userName;
    private Integer userId;
    private Set<ERole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userName;
    }

}

