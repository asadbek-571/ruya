package uz.ruya.mobile.core.rest.entity.onboarding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.DeviceType;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "onboardings", schema = BaseScheme.CORE)
public class Onboarding extends BaseEntityLong {

    @Transient
    private Integer titleKey = 1;
    @Transient
    private Integer descriptionKey = 2;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "image")
    private String image;

    @Column(name = "version")
    private String version;

    @Column(name = "onboarding_order")
    private Integer order;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.FALSE;

    @OneToMany(mappedBy = "onboarding")
    private List<OnboardingTranslate> translate;

}
