package uz.ruya.mobile.core.rest.peyload.res.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;
import uz.ruya.mobile.core.rest.peyload.res.ResAmount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/6/2025 3:04 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResAnnouncementOne implements Serializable {

    private Long id;
    private String title;
    private String date;
    private ResAmount amount;
    private ResImg icon;
    private Boolean isApplied = false;
    private Integer appliedQty;
    private String addressName;

    public ResAnnouncementOne(Announcement ann) {
        this.id = ann.getId();
        this.title = ann.getTitle();
        this.appliedQty = ann.getAppliedQty();
        this.date = DateUtils.monthFormat(ann.getCreatedAt());

        if (CoreUtils.isPresent(ann.getAddress())) {
            this.addressName = ann.getAddress().getName();
        }
        if (CoreUtils.isPresent(ann.getSpecialization()) && CoreUtils.isPresent(ann.getSpecialization().getIcon())) {
            this.icon = FileUtils.getCategoryIcon(ann.getSpecialization().getIcon());
        } else {
            this.icon = FileUtils.getCategoryIcon("default");
        }

        if (CoreUtils.isPresent(ann.getPrice())) {
            this.amount = new ResAmount(ann.getPrice(), ann.getCurrency());
        }

        if (CoreUtils.isPresent(ann.getUser())) {

            var user = ann.getUser();
            var contact = new Contact();

            if (CoreUtils.isPresent(user.getPhone())) {
                contact.setPhone(user.getPhone());
            }
            if (CoreUtils.isPresent(user.getEmail())) {
                contact.setEmail(user.getEmail());
            }
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contact implements Serializable {
        private String phone;
        private String email;
    }
}
