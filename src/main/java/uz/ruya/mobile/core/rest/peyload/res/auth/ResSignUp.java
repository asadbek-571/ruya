package uz.ruya.mobile.core.rest.peyload.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 11:42 AM 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResSignUp implements Serializable {

    @JsonProperty("message")
    private String message;

}
