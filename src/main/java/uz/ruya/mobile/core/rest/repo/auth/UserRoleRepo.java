package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.user.UserRole;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepo extends BaseRepositoryUUID<UserRole> {

    Optional<UserRole> findFirstByType(UserRoleType type);

}
