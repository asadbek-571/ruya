package uz.ruya.mobile.core.rest.peyload.req.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 Asadbek Kushakov 1/3/2025 10:36 AM 
 */
@Getter
@Setter
@ToString
public class ReqSignUp implements Serializable {

    private UUID identity;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
