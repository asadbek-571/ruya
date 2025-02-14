package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;

import java.util.List;

@Repository
public interface AnnouncementRepo extends BaseRepositoryLong<Announcement> {
    List<Announcement> findAllByUser(UserProfile user);

    List<Announcement> findAllByIdIn(List<Long> savedAnnoucementIdList);
}