package uz.ruya.mobile.core.rest.peyload.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.ruya.mobile.core.rest.entity.auth.User;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResTokenRefresh implements Serializable {

    @JsonProperty("user")
    private ResUserSimple user;

    @JsonProperty("access")
    private ResUserAccess access;


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResUserSimple implements Serializable {
        private String phone;
        private String username;
        private Boolean isPremium = Boolean.FALSE;

        public ResUserSimple(User authUser) {
            this.phone = authUser.getPhone();
            this.username = authUser.getUsername();
            this.isPremium = authUser.getIsPremium();
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResUserAccess implements Serializable {
        private UUID accessToken;
        private Long accessTokenExpireDate;
    }

}
