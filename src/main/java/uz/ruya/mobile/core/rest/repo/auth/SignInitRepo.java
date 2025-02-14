package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.auth.SignInit;
import uz.ruya.mobile.core.rest.enums.SignStatus;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SignInitRepo extends BaseRepositoryUUID<SignInit> {
    Optional<SignInit> findByIdAndStatus(UUID id, SignStatus status);
}