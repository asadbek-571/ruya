package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;

public interface AnnouncementService {

    ResPaging<ResAnnouncementOne> announcementPage(ReqAnnouncement request);

    SuccessMessage addJobAnnouncement(ReqAddAnnouncement request) throws EntityNotFoundException;

}
