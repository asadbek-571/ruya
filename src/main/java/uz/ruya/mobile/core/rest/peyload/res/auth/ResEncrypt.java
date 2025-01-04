package uz.ruya.mobile.core.rest.peyload.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 12:30 PM 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResEncrypt implements Serializable {
    @JsonProperty("encryptKey")
    private String encryptKey;
}
