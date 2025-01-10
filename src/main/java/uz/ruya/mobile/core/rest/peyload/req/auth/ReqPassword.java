package uz.ruya.mobile.core.rest.peyload.req.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/9/2025 5:53 PM 
 */

@Getter
@Setter
public class ReqPassword implements Serializable {
    private String password;
    private String publicKey;
}
