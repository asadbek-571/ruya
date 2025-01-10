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
public class ResSignCodeResend implements Serializable {

    @JsonProperty("identity")
    private UUID identity;

    @JsonProperty("message")
    private String message;

}
