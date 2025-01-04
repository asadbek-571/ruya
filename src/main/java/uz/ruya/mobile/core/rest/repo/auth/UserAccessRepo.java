package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.user.UserAccess;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface
UserAccessRepo extends BaseRepositoryUUID<UserAccess> {

    @Query(
            "FROM UserAccess a " +
                    " WHERE a.user.id = :userId " +
                    " AND a.refreshTokenExpire > CURRENT_TIMESTAMP "
    )
    Optional<UserAccess> findByUserId(@Param("userId") UUID userId);

}
