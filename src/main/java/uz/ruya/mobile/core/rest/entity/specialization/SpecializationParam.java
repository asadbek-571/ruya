package uz.ruya.mobile.core.rest.entity.specialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.enums.ParamType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specialization_parameters", schema = BaseScheme.CORE)
public class SpecializationParam extends BaseEntityLong {

    @Column(name = "label")
    private String label;

    @Column(name = "icon")
    private String icon;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "order_id")
    private Integer order;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "is_required")
    private Boolean isRequired = Boolean.FALSE;

    @Column(name = "min")
    private Integer min;

    @Column(name = "max")
    private Integer max;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "parent_id")
    private Long parentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ParamType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @OneToMany(mappedBy = "specializationParam")
    private List<SpecializationParamTranslate> translate = new ArrayList<>();


    public String getLabelTranslate() {
        if (CoreUtils.isPresent(this.getTranslate())) {

            Optional<SpecializationParamTranslate> optional = this.getTranslate().stream()
                    .filter(translate -> GlobalVar.getLANG().equals(translate.getLang())).findFirst();

            if (optional.isPresent()) {
                return optional.get().getName();
            }
        }
        return this.label;
    }

}
