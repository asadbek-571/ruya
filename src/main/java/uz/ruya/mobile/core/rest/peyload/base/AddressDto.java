package uz.ruya.mobile.core.rest.peyload.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 6:01 PM 
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto implements Serializable {

    private String addressName;
    private String addressLatitude;
    private String addressLongitude;

}
