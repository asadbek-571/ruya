package uz.ruya.mobile.core.rest.peyload.req.auth;

import lombok.Getter;
import lombok.Setter;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 10:30 AM 
 */

@Getter
@Setter
public class ReqLogin implements Serializable {
    private String username;
    private String password;
    private UserRoleType userRoleType;
}
