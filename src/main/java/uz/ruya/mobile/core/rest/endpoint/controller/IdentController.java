package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.excaption.*;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.IdentEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqLogin;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqSignUp;
import uz.ruya.mobile.core.rest.service.IdentityService;

import javax.management.relation.RoleNotFoundException;

/**
 Asadbek Kushakov 12/26/2024 1:18 PM 
 */

@RestController
@RequiredArgsConstructor
public class IdentController implements IdentEndpoint {

    private final IdentityService service;
    private final MessageSingleton messageSingleton;

    @Override
    public ResponseEntity<?> login(ReqLogin request) {
        try {
            var result = service.login(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (EntityNotFoundException | DecodeDataException | SignInitPasswordValidationException |
                 RoleNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> signUp(ReqSignUp request) {
        try {
            var result = service.signUp(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (UserExistException | RoleNotFoundException | DecodeDataException |
                 SignInitPasswordValidationException | EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> getEncryptKey() {
        try {
            var result = service.getEncryptKey();
            return GenericResponse.success(40000, "Success", result);
        } catch (PairKeyGenerationException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }
}
