package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.excaption.InvalidUserException;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.AnnouncementEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.service.AnnouncementService;

/**
 Asadbek Kushakov 1/3/2025 5:59 PM 
 */

@RestController
@RequiredArgsConstructor
public class AnnouncementController implements AnnouncementEndpoint {

    private final MessageSingleton messageSingleton;

    private final AnnouncementService service;


    @Override
    public ResponseEntity<?> addAnnouncement(ReqAddAnnouncement request) {
        try {
            var result = service.addJobAnnouncement(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> getAnnouncement(ReqLongId request) {
        try {
            var result = service.getAnnouncement(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> announcementPage(ReqAnnouncement request) {
        try {
            var result = service.announcementPage(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> announcementMy() {
        try {
            var result = service.announcementMy();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> toggleSaved(ReqLongId request) {
        try {
            if (CoreUtils.isEmpty(GlobalVar.getAuthUser()) || CoreUtils.isEmpty(GlobalVar.getAuthUser().getProfile())) {
                throw new InvalidUserException(20403, "Не авторизован");
            }
            var result = service.toggleSaved(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }


    @Override
    public ResponseEntity<?> mySavedAnnouncement() {
        try {
            if (CoreUtils.isEmpty(GlobalVar.getAuthUser()) || CoreUtils.isEmpty(GlobalVar.getAuthUser().getProfile())) {
                throw new InvalidUserException(20403, "Не авторизован");
            }
            var result = service.mySavedAnnouncement();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> addArchive(ReqLongId request) {
        try {
            var result = service.addArchive(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

}
