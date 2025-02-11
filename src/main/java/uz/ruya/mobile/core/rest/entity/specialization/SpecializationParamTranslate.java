package uz.ruya.mobile.core.rest.entity.specialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specialization_param_translate", schema = BaseScheme.CORE)
public class SpecializationParamTranslate extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Lang lang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_param_id")
    private SpecializationParam specializationParam;

}
