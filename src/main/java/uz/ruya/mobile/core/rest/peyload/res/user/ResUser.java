package uz.ruya.mobile.core.rest.peyload.res.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private AddressDto address;
    private String registrationDate;
    private Boolean needFCM = Boolean.FALSE;
    private Boolean isPremium = Boolean.FALSE;

}
