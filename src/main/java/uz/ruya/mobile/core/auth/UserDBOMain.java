package uz.ruya.mobile.core.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDBOMain implements Serializable {

    private UserDBO user;
    private UserDBORole role;
    private UserDBOSecurity security;
    private UserDBOPassword password;
    private UserDBODevice device;
}
