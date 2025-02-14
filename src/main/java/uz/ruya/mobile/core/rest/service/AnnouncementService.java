package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.excaption.InvalidUserException;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOneFull;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResMyAnnouncementList;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResMySavedAnnouncementList;

public interface AnnouncementService {

    SuccessMessage addJobAnnouncement(ReqAddAnnouncement request) throws EntityNotFoundException;

    ResPaging<ResAnnouncementOne> announcementPage(ReqAnnouncement request) throws EntityNotFoundException;

    ResAnnouncementOneFull getAnnouncement(ReqLongId request) throws EntityNotFoundException;

    ResMyAnnouncementList announcementMy();

    SuccessMessage toggleSaved(ReqLongId request) throws InvalidUserException;

    SuccessMessage addArchive(ReqLongId request);

    ResMySavedAnnouncementList mySavedAnnouncement();
}
