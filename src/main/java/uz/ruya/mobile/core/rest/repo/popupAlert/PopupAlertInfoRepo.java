package uz.ruya.mobile.core.rest.repo.popupAlert;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlertInfo;

import java.util.UUID;

@Repository
public interface PopupAlertInfoRepo extends BaseRepositoryLong<PopupAlertInfo> {

    boolean existsByPopupIdAndUserIdAndDeviceType(UUID popupId, UUID userId, DeviceType deviceType);

}