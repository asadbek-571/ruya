package uz.ruya.mobile.core.rest.peyload.req.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 10:30 AM 
 */

@Getter
@Setter
public class ReqLogin implements Serializable {

    @Schema(description = "username or email", example = "test@gmail.com")
    private String username;

    @Schema(description = "encrypt password", example = "HJjkasndujsekrn...")
    private String password;

    @Schema(description = "COMPANY or CANDIDATE", example = "CANDIDATE")
    private UserRoleType userRoleType;
}
