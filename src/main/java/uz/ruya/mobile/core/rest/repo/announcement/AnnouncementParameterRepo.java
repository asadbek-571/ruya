package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.announcement.AnnouncementParameter;

import java.util.Optional;

@Repository
public interface AnnouncementParameterRepo extends BaseRepositoryLong<AnnouncementParameter> {

    Optional<AnnouncementParameter> findByKeyAndAnnouncement(String key, Announcement announcement);
    Optional<AnnouncementParameter> findByValueAndAnnouncement(String value, Announcement announcement);
}