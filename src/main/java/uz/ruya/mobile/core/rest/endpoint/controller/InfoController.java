package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.InfoEndpoint;
import uz.ruya.mobile.core.rest.service.InfoService;

/**
 Asadbek Kushakov 1/8/2025 12:25 PM 
 */

@RestController
@RequiredArgsConstructor
public class InfoController implements InfoEndpoint {

    private final MessageSingleton messageSingleton;
    private final InfoService infoService;

    @Override
    public ResponseEntity<?> popupAlertList() {
        try {
            var result = infoService.popupAlertList();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> onboardingList() {
        try {
            var result = infoService.getOnboardingList();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }
}
