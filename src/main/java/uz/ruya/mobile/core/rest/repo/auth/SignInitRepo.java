package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.user.SignInit;

import java.util.Optional;

@Repository
public interface SignInitRepo extends BaseRepositoryUUID<SignInit> {

    Optional<SignInit> findByIpAndDeviceId(String ip, String deviceId);

}