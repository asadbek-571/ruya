package uz.ruya.mobile.core.rest.peyload.req.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 10:36 AM 
 */
@Getter
@Setter
@ToString
public class ReqSignUp implements Serializable {

    private String fullName;
    private String username;
    private String password;
    private UserRoleType userRoleType;

}
