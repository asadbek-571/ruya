package uz.ruya.mobile.core.rest.peyload.req.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 Asadbek Kushakov 2/14/2025 12:53 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqEditProfile implements Serializable {

    private String firstName;
    private String lastName;
    private String email;

}
