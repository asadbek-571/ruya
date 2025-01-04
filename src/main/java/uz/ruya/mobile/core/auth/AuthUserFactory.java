package uz.ruya.mobile.core.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthUserFactory {

    public static AuthUser create(
            String username,
            String password,
            UserDBOMain user,
            UserProfile profile,
            Set<String> permissions
    ) {
        return new AuthUser(
                username,
                password,
                user,
                profile,
                mapToGrantedAuthorities(permissions)
        );
    }

    private static Collection<GrantedAuthority> mapToGrantedAuthorities(Set<String> permissions) {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
