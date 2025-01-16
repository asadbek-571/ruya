package uz.ruya.mobile.core.rest.peyload.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ReqTokenRefresh implements Serializable {

    @JsonProperty("accessToken")
    private UUID accessToken;

}
