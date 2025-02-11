package uz.ruya.mobile.core.rest.peyload.res.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.peyload.res.ResAmount;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResAddressList;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationParam;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/6/2025 3:04 PM 
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResAnnouncementOneFull implements Serializable {

    private Long id;
    private String title;
    private String description;
    private ResAmount amount;
    private Contact contact;
    private ResAddressList.ResAddressOne address;
    private ResSpecializationParam otherInfo;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contact implements Serializable {
        private String phone;
        private String email;

        public Contact(UserProfile user) {
            this.phone = user.getPhone();
            this.email = user.getEmail();
        }
    }

}
