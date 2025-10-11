package com.ryzera.railwayservice.security;

import com.ryzera.railwayservice.enums.CommonActiveStatus;
import com.ryzera.railwayservice.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.ryzera.railwayservice.enums.UserStatus.ACTIVE;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.getRoles() == null) {
            return authorities;
        }

        // Add granular authorities (e.g., "parcel:manage")
        user.getRoles().stream()
                .filter(role -> role.getStatus() == CommonActiveStatus.ACTIVE && role.getAuthorities() != null)
                .flatMap(role -> role.getAuthorities().stream())
                .filter(authority -> authority.getStatus() == CommonActiveStatus.ACTIVE)
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .forEach(authorities::add);

        // Add roles themselves (e.g., "ROLE_ADMIN")
        user.getRoles().stream()
                .filter(role -> role.getStatus() == CommonActiveStatus.ACTIVE)
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .forEach(authorities::add);

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getMobileNumber();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == ACTIVE;
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

}
