package uz.ruya.mobile.core.rest.repo.saved;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.saved.SavedAd;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;

import java.util.List;
import java.util.Optional;

public interface SavedAdRepo extends JpaRepository<SavedAd, Long> {
    @Query("select s.ad.id from SavedAd s where s.user.id = ?1 order by s.ad.id")
    List<Long> findAllByUserIdOrderByAdId(Long id);

    Optional<SavedAd> findByUserAndAd(UserProfile user, Announcement announcement);
}