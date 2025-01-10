package uz.ruya.mobile.core.rest.repo.auth;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryUUID;
import uz.ruya.mobile.core.rest.entity.auth.User;

import java.util.Optional;

@Repository
public interface UserRepo extends BaseRepositoryUUID<User> {

    Optional<User> findByUsername(String username);
}
