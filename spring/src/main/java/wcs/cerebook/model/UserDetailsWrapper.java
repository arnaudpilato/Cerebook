package wcs.cerebook.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wcs.cerebook.entity.CerebookUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsWrapper implements UserDetails {
    private final CerebookUser cerebookUser;

    public UserDetailsWrapper(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return cerebookUser.getPassword();
    }

    @Override
    public String getUsername() {
        return cerebookUser.getUsername();
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

    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }
}
