package uz.ruya.mobile.core.rest.entity.onboarding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "onboarding_translate", schema = BaseScheme.CORE)
public class OnboardingTranslate extends BaseEntityLong {

    @Column(name = "key")
    private Integer key;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Lang lang;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Onboarding onboarding;

}
