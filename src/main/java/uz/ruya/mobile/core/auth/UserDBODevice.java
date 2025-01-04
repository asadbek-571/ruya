package uz.ruya.mobile.core.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.config.core.DeviceType;


import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDBODevice implements Serializable {

    private DeviceType deviceType;
    private String deviceId;
    private String deviceModel;
}
