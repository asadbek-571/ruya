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
import uz.ruya.mobile.core.rest.peyload.req.auth.*;
import uz.ruya.mobile.core.rest.service.IdentityService;

/**
 Asadbek Kushakov 12/26/2024 1:18 PM 
 */

@RestController
@RequiredArgsConstructor
public class IdentController implements IdentEndpoint {

    private final IdentityService service;
    private final MessageSingleton messageSingleton;

    @Override
    public ResponseEntity<?> signUserCheck(ReqSignUserCheck request) {
        try {
            var result = service.signUserCheck(request.getUsername());
            return GenericResponse.success(40000, "Success", result);
        } catch (ExternalServiceException | FraudClientServiceException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    public ResponseEntity<?> signUserVerify(ReqSignUserVerify request) {
        try {
            var result = service.signUserVerify(request.getIdentity(), request.getCode());
            return GenericResponse.success(40000, "Success", result);
        } catch (SignInitNotFoundException | SignInitCodeIncorrectException | SignInitCodeExpireException |
                 SignInitExpireException | SignInitStatusIncorrectException | PairKeyGenerationException |
                 SignInitCodeRetryException | ExternalServiceException | FraudClientServiceException |
                 EntityNotFoundException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> signIn(ReqSignIn request) {
        try {
            var result = service.signIn(request.getIdentity(), request.getPassword());
            return GenericResponse.success(40000, "Success", result);
        } catch (SignInitNotFoundException | SignInitExpireException | SignInitStatusIncorrectException |
                 DecodeDataException | UserNotFoundException | UserBlockedException |
                 SignInitPasswordIncorrectException | SignInitPasswordTryException | ExternalServiceException |
                 FraudClientServiceException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }


    @Override
    public ResponseEntity<?> signUp(ReqSignUp request) {
        try {
            var result = service.signUp(request.getIdentity(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
            return GenericResponse.success(40000, "Success", result);
        } catch (SignInitNotFoundException | SignInitExpireException | SignInitStatusIncorrectException |
                 DecodeDataException th) {
            return GenericResponse.error(20000, th.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> resendCode(ReqSignCodeResend code) {
        try {
            var result = service.codeResend(code.getIdentity());
            return GenericResponse.success(40000, "Success", result);
        } catch (SignInitNotFoundException | SignInitExpireException | SignInitStatusIncorrectException |
                 SignInitCodeResendException | ExternalServiceException | FraudClientServiceException e) {
            return GenericResponse.error(20000, e.getMessage());
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, "Неизвестная ошибка");
        }
    }

    @Override
    public ResponseEntity<?> agreementURl() {
        try {
            var result = service.getAgreementUrl();
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, "Неизвестная ошибка");
        }
    }

    @Override
    public ResponseEntity<?> encPassword(ReqPassword request) {
        try {
            var result = service.encPassword(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, "Неизвестная ошибка");
        }
    }
}
