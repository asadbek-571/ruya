package uz.ruya.mobile.core.rest.entity.popupAlert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.DeviceType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "popup_alert_infos", schema = BaseScheme.CORE)
public class PopupAlertInfo extends BaseEntityLong {

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "popup_id")
    private UUID popupId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

}