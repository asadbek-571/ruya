package uz.ruya.mobile.core.rest.peyload.res.onboarding;

import lombok.*;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.onboarding.Onboarding;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;


import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResOnboarding implements Serializable {

    private UUID uuid;

    private String title;

    private String description;

    private ResImg image;

    public ResOnboarding(Onboarding onboarding) {
        this.uuid = onboarding.getUuid();
        this.title = onboarding.getTitle();
        this.description = onboarding.getDescription();
        this.image = FileUtils.getOnboardingImage(onboarding.getImage());
    }

}
