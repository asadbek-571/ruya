package uz.ruya.mobile.core.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.UserGender;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDBO implements Serializable {

    private UUID id;
    private String userNO;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Integer age;
    private String fcmToken;
    private Boolean isPremium;
    private LocalDateTime registrationDate;
    private BaseStatus status = BaseStatus.BLOCKED;
    private UserGender gender = UserGender.UNKNOWN;
}
