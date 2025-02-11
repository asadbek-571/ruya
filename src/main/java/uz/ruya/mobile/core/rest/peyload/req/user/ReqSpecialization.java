package uz.ruya.mobile.core.rest.peyload.req.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/28/2025 2:45 PM 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqSpecialization implements Serializable {

    private Long categoryId;
    private Long addressId;
    private String title;
    private ReqAmount price;
    private String description;
    private List<ReqSpecialization.ReqSpecializationParam> params = new ArrayList<>();

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReqSpecializationParam implements Serializable {

        private Long id;
        private String code;
        private String value;
        private List<String> multiValue = new ArrayList<>();

    }


}
