package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.JobEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.job.ReqAddEmployerAds;
import uz.ruya.mobile.core.rest.service.JobService;

/**
 Asadbek Kushakov 1/3/2025 5:59 PM 
 */

@RestController
@RequiredArgsConstructor
public class JobController implements JobEndpoint {

    private final MessageSingleton messageSingleton;

    private final JobService service;


    @Override
    public ResponseEntity<?> addEmployerAds(ReqAddEmployerAds request) {
        try {
            var result = service.addEmployerAds(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

}
