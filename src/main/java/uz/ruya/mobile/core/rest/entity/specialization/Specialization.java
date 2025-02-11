package uz.ruya.mobile.core.rest.entity.specialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.enums.SpecializationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specializations", schema = BaseScheme.CORE)
public class Specialization extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "order_id")
    private Integer orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SpecializationType type;

    @OneToMany
    @JoinColumn(name = "specialization_id")
    private List<SpecializationTranslate> translate = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "specialization_id")
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "specialization_id")
    private List<SpecializationParam> params = new ArrayList<>();

    public String getLabelTranslate() {
        if (CoreUtils.isPresent(this.getTranslate())) {

            Optional<SpecializationTranslate> optional = this.getTranslate().stream()
                    .filter(translate -> GlobalVar.getLANG().equals(translate.getLang())).findFirst();

            if (optional.isPresent()) {
                return optional.get().getName();
            }
        }
        return this.name;
    }

}
