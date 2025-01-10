package uz.ruya.mobile.core.rest.repo.user;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepository;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserProfileRepo extends BaseRepository<UserProfile> {

    Optional<UserProfile> findUserProfileByUuid(UUID uuid);

    Optional<UserProfile> findByPhone(String phone);

    List<UserProfile> findAllByPhoneIn(Set<String> phoneSet);

    Optional<UserProfile> findByUuid(UUID uuid);

    @Transactional
    void deleteByUuid(UUID uuid);

}
