package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.rest.entity.auth.UserAccess;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface
UserAccessRepo extends BaseRepositoryUUID<UserAccess> {

    @Query(
            "FROM UserAccess a " +
                    " WHERE a.accessToken = :accessToken " +
                    " AND a.refreshTokenExpire > CURRENT_TIMESTAMP"
    )
    Optional<UserAccess> findByAccessToken(@Param("accessToken") UUID accessToken);

    @Query(
            "FROM UserAccess a " +
                    "WHERE a.accessToken = :accessToken " +
                    "AND a.refreshTokenExpire > CURRENT_TIMESTAMP " +
                    "AND a.user.status = 'ACTIVE'" +
                    "AND a.type = :deviceType"
    )
    Optional<UserAccess> findByActiveAuthAndWithType(
            @Param("accessToken") UUID accessToken,
            @Param("deviceType") DeviceType type
    );

}
