package uz.ruya.mobile.core.rest.peyload.res.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.peyload.res.ResAmount;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/6/2025 3:04 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResAnnouncementOneFull implements Serializable {

    private Long id;
    private String title;
    private String date;
    private ResAmount amount;
    private Boolean isApplied = false;
    private Integer appliedQty;
    private String addressName;

    public ResAnnouncementOneFull(Announcement ann) {
        this.id = ann.getId();
        this.title = ann.getTitle();
        this.appliedQty = ann.getAppliedQty();
        this.date = DateUtils.monthFormat(ann.getCreatedAt());

        if (CoreUtils.isPresent(ann.getAddress())) {
            this.addressName = ann.getAddress();
        }

    }
}
