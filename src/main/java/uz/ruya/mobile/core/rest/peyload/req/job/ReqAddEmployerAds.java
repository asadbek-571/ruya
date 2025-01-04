package uz.ruya.mobile.core.rest.peyload.req.job;

import lombok.*;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.enums.ExperienceLevel;
import uz.ruya.mobile.core.rest.enums.JobListingType;
import uz.ruya.mobile.core.rest.peyload.req.ReqAddress;
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
public class ReqAddEmployerAds implements Serializable {

    private String jobName;
    private String description;
    private ReqAddress address;
    private JobListingType type;
    private ReqAmount price;
    private ExperienceLevel experienceLevel;
    private List<String> benefits = new ArrayList<>();
    private List<String> requirements = new ArrayList<>();

}
