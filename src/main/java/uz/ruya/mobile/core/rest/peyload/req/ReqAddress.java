package uz.ruya.mobile.core.rest.peyload.req;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "selected address name", example = "Chiyoda City, Tokyo 100-8111")
    private String addressName;
    @Schema(description = "coordinate latitude", example = "35.68391717717464")
    private String addressLatitude;
    @Schema(description = "coordinate longitude", example = "139.75401836975897")
    private String addressLongitude;

}
