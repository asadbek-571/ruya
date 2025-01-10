package uz.ruya.mobile.core.rest.peyload.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResSignUserVerify implements Serializable {

    @JsonProperty("identity")
    private UUID identity;

    @JsonProperty("encryptKey")
    private String encryptKey;

    @JsonProperty("isReg")
    private Boolean isReg = Boolean.FALSE;

}
