package uz.ruya.mobile.core.rest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntity;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.DevMode;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.config.core.Lang;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_device", schema = BaseScheme.AUTH,
        uniqueConstraints = {
                @UniqueConstraint(name = "user_device_uuid_unique", columnNames = {"user_uuid"})
        }
)
public class UserDevice extends BaseEntity {

    @Transient
    private final String seqName = "user_device_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = seqName)
    @SequenceGenerator(name = seqName, sequenceName = seqName, allocationSize = 1)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUUID;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "app_build")
    private String appBuild;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "os")
    private String os;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "dev_mode")
    @Enumerated(EnumType.STRING)
    private DevMode devMode;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;

}
