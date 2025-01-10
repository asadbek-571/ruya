package uz.ruya.mobile.core.rest.peyload.req.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.enums.JobType;
import uz.ruya.mobile.core.rest.enums.WorkingType;
import uz.ruya.mobile.core.rest.peyload.base.AddressDto;
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
    private String title;
    private String description;
    private AddressDto address;
    private JobType jobType;
    private AnnouncementType type;
    private WorkingType workingType;
    private ReqAmount price;
    private List<String> benefits = new ArrayList<>();
    private List<String> requirements = new ArrayList<>();

}
