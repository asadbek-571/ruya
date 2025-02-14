package uz.ruya.mobile.core.rest.peyload.req.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 Asadbek Kushakov 2/14/2025 12:52 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqChangePassword implements Serializable {
    private String oldPassword;
    private String newPassword;
}
