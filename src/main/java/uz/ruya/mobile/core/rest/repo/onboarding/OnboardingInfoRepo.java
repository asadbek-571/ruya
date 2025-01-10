package uz.ruya.mobile.core.rest.repo.onboarding;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.rest.entity.onboarding.OnboardingInfo;

import java.util.UUID;

@Repository
public interface OnboardingInfoRepo extends BaseRepositoryLong<OnboardingInfo> {

    boolean existsByUserIdAndDeviceTypeAndVersion(UUID userId, DeviceType deviceType, String version);

}