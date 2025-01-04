package uz.ruya.mobile.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;


import java.util.Collection;

@Data
@AllArgsConstructor
public class AuthUser implements UserDetails {

    private final String username;
    private final String password;
    private final UserDBOMain user;
    private final UserProfile profile;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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

}
