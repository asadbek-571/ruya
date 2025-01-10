package uz.ruya.mobile.core.rest.peyload.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResAgreementUrl implements Serializable {

    @JsonProperty("agreementUrl")
    private String agreementUrl;

}
