package uz.ruya.mobile.core.rest.peyload.req.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/3/2025 6:01 PM 
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqAddAnnouncement implements Serializable {

    private Long categoryId;
    private Long addressId;
    private String title;
    private ReqAmount price;
    private String description;
    private AnnouncementType type;
    private List<ReqAddAnnouncementParam> params = new ArrayList<>();

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReqAddAnnouncementParam implements Serializable {

        private Long id;
        private String code;
        private String value;
        private List<String> multiValue = new ArrayList<>();

    }

}
