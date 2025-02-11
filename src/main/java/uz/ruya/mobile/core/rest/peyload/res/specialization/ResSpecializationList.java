package uz.ruya.mobile.core.rest.peyload.res.specialization;

import lombok.*;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.enums.SpecializationType;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/10/2025 11:43 AM 
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResSpecializationList implements Serializable {

    private List<ResSpecializationOne> list = new ArrayList<>();
    private Integer count;

    public ResSpecializationList(List<ResSpecializationOne> list) {
        this.count = list.size();
        this.list = list;
    }

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResSpecializationOne implements Serializable {

        private Long id;
        private ResImg icon;
        private String name;
        private Integer totalAnnouncement;
        private SpecializationType type;

        public ResSpecializationOne(Specialization specialization) {
            this.id = specialization.getId();
            this.name = specialization.getName();
            this.type = specialization.getType();
            this.icon = FileUtils.getCategoryIcon(specialization.getIcon());
        }
    }

}
