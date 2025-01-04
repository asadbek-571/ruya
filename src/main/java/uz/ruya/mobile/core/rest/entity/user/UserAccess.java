package uz.ruya.mobile.core.rest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityUUID;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.DeviceType;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_access", schema = BaseScheme.AUTH)
public class UserAccess extends BaseEntityUUID {

    @Transient
    private final String fUserAccessUser = "user_access_user_fkey";

    @Column(name = "refresh_token")
    private UUID refreshToken;

    @Column(name = "refresh_token_expire")
    private LocalDateTime refreshTokenExpire;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expire")
    private LocalDateTime accessTokenExpire;

    @Column(name = "private_key", columnDefinition = "TEXT")
    private String privateKey;

    @Column(name = "public_key", columnDefinition = "TEXT")
    private String publicKey;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = fUserAccessUser))
    private User user;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

}
