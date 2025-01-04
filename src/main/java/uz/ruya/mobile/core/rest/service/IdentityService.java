package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.auth.UserDBOMain;
import uz.ruya.mobile.core.config.excaption.*;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqLogin;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqSignUp;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResEncrypt;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResLogin;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResSignUp;

import javax.management.relation.RoleNotFoundException;

public interface IdentityService {
    UserDBOMain validateToken(String authorization) throws InvalidTokenException;

    ResLogin login(ReqLogin request) throws EntityNotFoundException, DecodeDataException, SignInitPasswordValidationException, RoleNotFoundException;

    ResSignUp signUp(ReqSignUp request) throws UserExistException, RoleNotFoundException, DecodeDataException, SignInitPasswordValidationException, EntityNotFoundException;

    ResEncrypt getEncryptKey() throws PairKeyGenerationException;
}
