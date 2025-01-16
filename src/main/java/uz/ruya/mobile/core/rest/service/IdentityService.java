package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.auth.UserDBOMain;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.*;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqPassword;
import uz.ruya.mobile.core.rest.peyload.res.auth.*;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface IdentityService {

    ResSignUserCheck signUserCheck(
            String username
    ) throws
            ExternalServiceException,
            FraudClientServiceException;

    ResSignUserVerify signUserVerify(
            UUID identity,
            String code
    ) throws
            SignInitNotFoundException,
            SignInitCodeIncorrectException,
            SignInitCodeExpireException,
            SignInitExpireException,
            SignInitStatusIncorrectException,
            PairKeyGenerationException,
            SignInitCodeRetryException,
            ExternalServiceException,
            FraudClientServiceException, EntityNotFoundException;

    ResSignIn signIn(
            UUID identity,
            String password
    ) throws
            SignInitNotFoundException,
            SignInitExpireException,
            SignInitStatusIncorrectException,
            DecodeDataException,
            UserNotFoundException,
            UserBlockedException,
            SignInitPasswordIncorrectException,
            SignInitPasswordTryException,
            ExternalServiceException,
            FraudClientServiceException;


    ResSignUp signUp(
            UUID identity,
            String firstName,
            String lastName,
            String email,
            String password
    ) throws
            SignInitNotFoundException,
            SignInitExpireException,
            SignInitStatusIncorrectException,
            DecodeDataException,
            UserExistException,
            RoleNotFoundException,
            SignInitPasswordValidationException;

    ResSignCodeResend codeResend(
            UUID identity
    ) throws
            SignInitNotFoundException,
            SignInitExpireException,
            SignInitStatusIncorrectException,
            SignInitCodeResendException,
            ExternalServiceException,
            FraudClientServiceException;

    ResAgreementUrl getAgreementUrl();

    UserDBOMain validateToken(
            String token
    ) throws
            InvalidTokenException;

    ResTokenRefresh tokenRefresh(
            UUID accessToken,
            HttpServletResponse httpServletResponse
    ) throws NotAuthorizationException;

    SuccessMessage encPassword(ReqPassword request);
}
