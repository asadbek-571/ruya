package uz.ruya.mobile.core.rest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityUUID;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.DevMode;
import uz.ruya.mobile.core.rest.enums.SignStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sign_init", schema = BaseScheme.AUTH)
public class SignInit extends BaseEntityUUID {

    @Column(name = "expire")
    private LocalDateTime expire;

    @Column(name = "username")
    private String username;

    @Column(name = "code")
    private String code;

    @Column(name = "code_expire")
    private LocalDateTime codeExpire;

    @Column(name = "try_count")
    private Short tryCount = 0;

    @Column(name = "code_count")
    private Short codeCount = 0;

    @Column(name = "private_key", columnDefinition = "TEXT")
    private String privateKey;

    @Column(name = "public_key", columnDefinition = "TEXT")
    private String publicKey;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "dev_mode")
    @Enumerated(EnumType.STRING)
    private DevMode devMode;

    @Column(name = "ip")
    private String ip;

    @Column(name = "agent")
    private String agent;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "is_reg")
    private Boolean isReg = Boolean.FALSE;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SignStatus status = SignStatus.CHECK;

    @Column(name = "status_message", columnDefinition = "TEXT")
    private String statusMessage;

}
