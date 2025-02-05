package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.UserEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqAboutInfo;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqAttachCv;
import uz.ruya.mobile.core.rest.service.UserService;

/**
 Asadbek Kushakov 1/10/2025 11:01 AM 
 */

@RestController
@RequiredArgsConstructor
public class UserController implements UserEndpoint {

    private final UserService userService;
    private final MessageSingleton messageSingleton;

    @Override
    public ResponseEntity<?> me() {
        return GenericResponse.success(20000, "success");
    }

    @Override
    public ResponseEntity<?> uploadCv(ReqAttachCv request) {
        try {
            var result = userService.uploadCv(request.getAttachmentId(), request.getIsMainCv());
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> addDescription(ReqAboutInfo request) {
        try {
            var result = userService.addDescription(request.getText());
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> toggleSkill(ReqLongId request) {
        try {
            var result = userService.toggleSkill(request.getId());
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }
}
