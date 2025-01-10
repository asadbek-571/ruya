package uz.ruya.mobile.core.rest.peyload.res.onboarding;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResOnboardingList implements Serializable {

    private List<ResOnboarding> onboardings;

}
