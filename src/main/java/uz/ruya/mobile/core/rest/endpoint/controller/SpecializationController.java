package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.SpecializationEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecialization;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecializationParam;
import uz.ruya.mobile.core.rest.service.SpecializationService;

/**
 Asadbek Kushakov 2/10/2025 11:55 AM 
 */

@RestController
@RequiredArgsConstructor
public class SpecializationController implements SpecializationEndpoint {

    private final MessageSingleton messageSingleton;

    private final SpecializationService service;


    @Override
    public ResponseEntity<?> specializationList(ReqSpecialization request) {
        try {
            var result = service.specializationList(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> getParam(ReqSpecializationParam request) {
        try {
            var result = service.getParam(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

}
