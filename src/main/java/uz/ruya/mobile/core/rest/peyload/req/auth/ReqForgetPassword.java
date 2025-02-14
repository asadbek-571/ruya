package uz.ruya.mobile.core.rest.peyload.req.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 Asadbek Kushakov 2/14/2025 2:30 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqForgetPassword implements Serializable {

    private UUID identity;
    private String password;

}
