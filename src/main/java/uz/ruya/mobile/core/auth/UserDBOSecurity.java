package uz.ruya.mobile.core.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDBOSecurity implements Serializable {

    private String prvKey;
    private String pubKey;
    private String password;
}
