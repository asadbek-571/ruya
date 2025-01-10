package uz.ruya.mobile.core.rest.repo.onboarding;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.rest.entity.onboarding.Onboarding;

import java.util.List;

@Repository
public interface OnboardingRepo extends BaseRepositoryLong<Onboarding> {

    List<Onboarding> findAllByVersionAndDeviceTypeAndIsActiveTrueOrderByOrderAsc(String version, DeviceType deviceType);

    Boolean existsByVersion(String version);

}