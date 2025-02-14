package uz.ruya.mobile.core.rest.peyload.res.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.auth.UserDBO;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.peyload.base.AddressDto;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/8/2025 12:37 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResUser implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthDate;
    private Integer age;
    private Boolean isAlert;
    private String avatarPath;
    private String registrationDate;
    private Boolean needFCM = Boolean.FALSE;
    private Boolean isPremium = Boolean.FALSE;

    public ResUser(UserProfile profile, UserDBO user) {
        this.avatarPath = FileUtils.avatarPath(String.valueOf(profile.getAvatarId()));
        this.registrationDate= DateUtils.dateTimeToFront(user.getRegistrationDate());
        this.needFCM = CoreUtils.isEmpty(user.getFcmToken());
        this.birthDate = profile.getBirthDateStr();
        this.isAlert = profile.getIsAlert();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.age = user.getAge();
    }
}
