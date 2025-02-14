package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.UserEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqEditProfile;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqSpecialization;
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
        try {
            var result = userService.me();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }


    @Override
    public ResponseEntity<?> attachAvatar(ReqSpecialization request) {
        try {
            var result = userService.attachAvatar(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> editProfile(ReqEditProfile request) {
        try {
            var result = userService.editProfile(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }
}
