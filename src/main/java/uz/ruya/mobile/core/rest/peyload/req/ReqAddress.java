package uz.ruya.mobile.core.rest.peyload.req;

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
public class ReqAddress implements Serializable {

    private String addressName;
    private String addressLatitude;
    private String addressLongitude;

}
