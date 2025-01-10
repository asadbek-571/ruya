package uz.ruya.mobile.core.rest.repo;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.auth.UserRole;
import uz.ruya.mobile.core.rest.enums.UserRoleType;

import java.util.Optional;

@Repository
public interface UserRoleRepo extends BaseRepositoryUUID<UserRole> {

    Optional<UserRole> findByType(UserRoleType type);

}