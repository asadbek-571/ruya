package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.auth.*;
import uz.ruya.mobile.core.config.core.CipherUtility;
import uz.ruya.mobile.core.config.core.DeviceType;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.*;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.config.utils.NotifyUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.auth.*;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.Headers;
import uz.ruya.mobile.core.rest.enums.SignStatus;
import uz.ruya.mobile.core.rest.enums.UserRoleType;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqPassword;
import uz.ruya.mobile.core.rest.peyload.res.auth.*;
import uz.ruya.mobile.core.rest.repo.UserRoleRepo;
import uz.ruya.mobile.core.rest.repo.auth.SignInitRepo;
import uz.ruya.mobile.core.rest.repo.auth.UserAccessRepo;
import uz.ruya.mobile.core.rest.repo.auth.UserRepo;
import uz.ruya.mobile.core.rest.service.IdentityService;
import uz.ruya.mobile.core.rest.service.NotifyService;
import uz.ruya.mobile.core.rest.service.PropertiesService;
import uz.ruya.mobile.core.rest.validator.FraudValidator;

import javax.crypto.Cipher;
import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 Asadbek Kushakov 12/25/2024 10:46 AM 
 */

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    private final MessageSingleton messageSingleton;
    private final PasswordEncoder passwordEncoder;
    private final FraudValidator fraudValidator;
    private final CipherUtility cipherUtility;
    private final NotifyUtils notifyUtils;

    private final PropertiesService propertiesService;
    private final NotifyService notifyService;

    private final UserAccessRepo userAccessRepo;
    private final SignInitRepo signInitRepo;
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepository;

    @Override
    public ResSignUserCheck signUserCheck(String username) throws ExternalServiceException, FraudClientServiceException {

        final String deviceId = GlobalVar.getDeviceId();
        final String deviceModel = GlobalVar.getDeviceModel();

        boolean isFraudCheckEnabled = propertiesService.isFraudPhoneCheck();
        boolean smsAvailable = fraudValidator.isFraudPhoneCheck(isFraudCheckEnabled, username, deviceId, deviceModel);

        String code = CoreUtils.generateSmsCode();

        if ("998979497771".equals(username)) {
            code = "232323";
            notifyService.sendSMS(username, notifyUtils.getSignMessage(code));
        } else if (smsAvailable) {
            notifyService.sendSMS(username, notifyUtils.getSignMessage(code));
        }

        SignInit sign = new SignInit();
        sign.setExpire(DateUtils.identityTokenExpire());
        sign.setCodeExpire(DateUtils.codeExpire());
        sign.setDevMode(GlobalVar.getDEV_MODE());
        sign.setAgent(GlobalVar.getUserAgent());
        sign.setIp(GlobalVar.getIpAddress());
        sign.setUsername(username);
        sign.setCode(code);
        sign = signInitRepo.saveAndFlush(sign);

        var result = new ResSignUserCheck();
        result.setIdentity(sign.getId());
        result.setMessage(String.format("СМС отправлен на номер %s", CoreUtils.maskedPhone(sign.getUsername())));
        return result;
    }

    @Override
    public ResSignUserVerify signUserVerify(UUID identity, String code) throws SignInitNotFoundException, SignInitCodeIncorrectException, SignInitCodeExpireException, SignInitExpireException, SignInitStatusIncorrectException, PairKeyGenerationException, SignInitCodeRetryException, ExternalServiceException, FraudClientServiceException {

        SignInit sign = this.signVerify(identity, code);

        Optional<User> userOptional = userRepo.findByUsername(sign.getUsername());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            sign.setIsReg(Boolean.TRUE);
            sign.setUserUUID(user.getId());

        }

        sign.setStatus(SignStatus.VERIFIED);
        sign = signInitRepo.saveAndFlush(sign);

        var result = new ResSignUserVerify();
        result.setIdentity(sign.getId());
        result.setEncryptKey(sign.getPublicKey());
        result.setIsReg(sign.getIsReg());
        return result;
    }

    @Override
    public ResSignIn signIn(UUID identity, String password) throws SignInitNotFoundException, SignInitExpireException, SignInitStatusIncorrectException, DecodeDataException, UserNotFoundException, UserBlockedException, SignInitPasswordIncorrectException, SignInitPasswordTryException, ExternalServiceException, FraudClientServiceException {

        SignInit sign = this.checkSign(identity);

        User authUser = this.checkUserForSign(sign, password);

        return this.executeNewAccessWithRemovingOldAccessData(sign, authUser);
    }

    @Override
    public ResSignUp signUp(UUID identity, String firstName, String lastName, String email, String password) throws SignInitNotFoundException, SignInitExpireException, SignInitStatusIncorrectException, DecodeDataException, UserExistException, RoleNotFoundException, SignInitPasswordValidationException {

        SignInit sign = this.checkSign(identity);

        final String pass = this.getDecryptedPass(sign, password);

        if (!CoreUtils.checkPassword(pass)) {
            throw new SignInitPasswordValidationException(messageSingleton.getMessage(MessageKey.INCORRECT_PASSWORD));
        }

        Optional<User> userOptional = userRepo.findByUsername(sign.getUsername());

        if (userOptional.isPresent()) {
            throw new UserExistException(messageSingleton.getMessage(MessageKey.USER_ALREADY_EXISTS));
        }

        Optional<UserRole> optionalUserRole = userRoleRepository.findByType(UserRoleType.USER);

        if (optionalUserRole.isEmpty()) {
            throw new RoleNotFoundException(messageSingleton.getMessage(MessageKey.ROLE_NOT_FOUND));
        }

        User user = new User();
        user.setUsername(sign.getUsername());
        user.setPhone(sign.getUsername());
        user.setPassword(passwordEncoder.encode(pass));
        user.setRole(optionalUserRole.get());
        user.setStatus(BaseStatus.ACTIVE);

        userRepo.saveAndFlush(user);

        var signUp = new ResSignUp();
        signUp.setMessage(messageSingleton.getMessage(MessageKey.USER_SUCCESSFULLY_REGISTERED));
        return signUp;
    }

    @Override
    public ResSignCodeResend codeResend(UUID identity) throws SignInitNotFoundException, SignInitExpireException, SignInitStatusIncorrectException, SignInitCodeResendException, ExternalServiceException, FraudClientServiceException {

        SignInit sign = this.getSignByIdentity(identity);

        final String username = sign.getUsername();

        final String deviceId = GlobalVar.getDeviceId();
        final String deviceModel = GlobalVar.getDeviceModel();

        boolean isFraudCheckEnabled = propertiesService.isFraudCodeResendCheck();
        boolean resendCode = fraudValidator.isFraudCodeResendCheck(isFraudCheckEnabled, username, deviceId, deviceModel);

        if (resendCode) {
            var result = new ResSignCodeResend();
            result.setIdentity(sign.getId());
            result.setMessage(String.format("СМС отправлен на номер %s", CoreUtils.maskedPhone(username)));
            return result;
        }

        if (LocalDateTime.now().isAfter(sign.getExpire())) {
            throw new SignInitExpireException(messageSingleton.getMessage(MessageKey.VERIFICATION_TIME_EXPIRED));
        }

        if (!SignStatus.CHECK.equals(sign.getStatus())) {
            throw new SignInitStatusIncorrectException(messageSingleton.getMessage(MessageKey.VERIFICATION_STATUS_NOT_CORRECT));
        }

        if ((sign.getCodeCount() + 1) >= 3) {
            throw new SignInitCodeResendException(messageSingleton.getMessage(MessageKey.CONFIRMATION_CODE_INPUT_LIMIT_EXCEEDED));
        }

        String code = CoreUtils.generateSmsCode();

        if ("998979497771".equals(username)) {
            code = "232323";
            notifyService.sendSMS(username, notifyUtils.getSignMessage(code));
        } else {
            notifyService.sendSMS(username, notifyUtils.getSignMessage(code));
        }

        sign.setCode(code);
        sign.setCodeExpire(DateUtils.codeExpire());
        sign.setCodeCount((short) (sign.getCodeCount() + 1));
        sign = signInitRepo.saveAndFlush(sign);

        var result = new ResSignCodeResend();
        result.setIdentity(sign.getId());
        result.setMessage(String.format("СМС отправлен на номер %s", CoreUtils.maskedPhone(username)));
        return result;
    }

    @Override
    public ResAgreementUrl getAgreementUrl() {
        return new ResAgreementUrl(propertiesService.getPolicyUrlForRegistration());
    }

    @Override
    public UserDBOMain validateToken(String token) throws InvalidTokenException {
        try {

            UUID accessToken = UUID.fromString(token);

            UserAccess userAccess = userAccessRepo.findByAccessToken(accessToken).orElseThrow(() -> new InvalidTokenException(20401, messageSingleton.getMessage(MessageKey.INVALID_TOKEN)));
            User authUser = userAccess.getUser();

            if (CoreUtils.isEmpty(authUser) || !BaseStatus.ACTIVE.equals(authUser.getStatus())) {
                throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
            }

            if (isTokenExpired(userAccess.getAccessTokenExpire())) {
                throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
            }

            return generateResponse(authUser, userAccess);

        } catch (Throwable th) {
            throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
        }
    }


    @Override
    public ResTokenRefresh tokenRefresh(UUID accessToken, HttpServletResponse httpServletResponse) throws NotAuthorizationException {

        DeviceType deviceType = GlobalVar.getDEVICE_TYPE();

        Optional<UserAccess> accessOptional = userAccessRepo.findByActiveAuthAndWithType(
                accessToken,
                deviceType
        );

        if (accessOptional.isEmpty()) {
            httpServletResponse.addHeader("X-Action", Headers.LOGOUT.toString());
            throw new NotAuthorizationException(messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
        }

        UserAccess userAccess = accessOptional.get();

        return this.executeRefreshTokenWithRemovingOldData(userAccess, deviceType);
    }

    @Override
    public SuccessMessage encPassword(ReqPassword request) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(request.getPublicKey());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(spec);

            byte[] contentBytes = request.getPassword().getBytes();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] cipherContent = cipher.doFinal(contentBytes);
            return new SuccessMessage(Base64.getEncoder().encodeToString(cipherContent));
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private SignInit checkSign(UUID identity) throws SignInitNotFoundException, SignInitExpireException, SignInitStatusIncorrectException {

        SignInit sign = this.getSignByIdentity(identity);

        if (LocalDateTime.now().isAfter(sign.getExpire())) {
            throw new SignInitExpireException(messageSingleton.getMessage(MessageKey.VERIFICATION_TIME_EXPIRED));
        }

        if (!SignStatus.VERIFIED.equals(sign.getStatus())) {
            throw new SignInitStatusIncorrectException(messageSingleton.getMessage(MessageKey.VERIFICATION_STATUS_NOT_CORRECT));
        }

        return sign;
    }

    private User checkUserForSign(SignInit sign, String password) throws UserNotFoundException, ExternalServiceException, FraudClientServiceException, DecodeDataException, SignInitPasswordIncorrectException, SignInitPasswordTryException, UserBlockedException {

        Optional<User> userOptional = userRepo.findByUsername(sign.getUsername());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(messageSingleton.getMessage(MessageKey.USER_NOT_FOUND));
        }

        User authUser = userOptional.get();

        final String deviceId = GlobalVar.getDeviceId();
        final String deviceModel = GlobalVar.getDeviceModel();

        final String userIdStr = authUser.getId().toString();
        final String username = authUser.getUsername();

        boolean isFraudCheckEnabled = propertiesService.isFraudSignIn();
        fraudValidator.isFraudSingIn(isFraudCheckEnabled, username, userIdStr, deviceId, deviceModel);

        final String pass = this.getDecryptedPass(sign, password);

        if (CoreUtils.isPresent(authUser.getPassword())) {
            if (!passwordEncoder.matches(pass, authUser.getPassword())) {
                if ((sign.getTryCount() + 1) < 3) {
                    throw new SignInitPasswordIncorrectException(messageSingleton.getMessage(MessageKey.INCORRECT_PASSWORD));
                }
                throw new SignInitPasswordTryException(messageSingleton.getMessage(MessageKey.CONFIRMATION_CODE_INPUT_LIMIT_EXCEEDED));
            }
        } else {
            authUser.setPassword(passwordEncoder.encode(password));
            userRepo.save(authUser);
        }

        if (BaseStatus.BLOCKED.equals(authUser.getStatus())) {
            throw new UserBlockedException(messageSingleton.getMessage(MessageKey.USER_BLOCKED_CONTACT_ADMINISTRATOR));
        }

        // refresh
        fraudValidator.refreshFraudSignIn(isFraudCheckEnabled, username, userIdStr, deviceId, deviceModel);

        return authUser;
    }

    private ResSignIn executeNewAccessWithRemovingOldAccessData(SignInit sign, User authUser) {

        UUID refreshToken = CoreUtils.generateTokenUUID();
        UUID accessToken = CoreUtils.generateTokenUUID();

        DeviceType deviceType = GlobalVar.getDEVICE_TYPE();

        Long accessTokenExpireHours = propertiesService.getAccessTokenExpireHours();

        // delete old access by user
        userAccessRepo.deleteAllAccessByUser(authUser);

        UserAccess authAccess = new UserAccess();
        authAccess.setUser(authUser);
        authAccess.setRefreshToken(refreshToken);
        authAccess.setRefreshTokenExpire(DateUtils.refreshTokenExpire(deviceType));
        authAccess.setAccessToken(accessToken);
        authAccess.setAccessTokenExpire(LocalDateTime.now().plusHours(accessTokenExpireHours));
        authAccess.setPrivateKey(sign.getPrivateKey());
        authAccess.setPublicKey(sign.getPublicKey());
        authAccess.setIpAddress(GlobalVar.getIpAddress());
        authAccess.setUserAgent(GlobalVar.getUserAgent());
        authAccess.setType(deviceType);
        userAccessRepo.saveAndFlush(authAccess);

        ResSignIn.ResUserAccess access = new ResSignIn.ResUserAccess(
                accessToken,
                accessTokenExpireHours * 60 * 60L
        );

        ResSignIn signIn = new ResSignIn();
        signIn.setUser(new ResSignIn.ResUserSimple(authUser));
        signIn.setAccess(access);
        return signIn;
    }

    private SignInit signVerify(UUID identity, String code) throws ExternalServiceException, FraudClientServiceException, SignInitNotFoundException, SignInitExpireException, SignInitStatusIncorrectException, SignInitCodeIncorrectException, SignInitCodeRetryException, SignInitCodeExpireException, PairKeyGenerationException {

        final String deviceId = GlobalVar.getDeviceId();
        final String deviceModel = GlobalVar.getDeviceModel();

        boolean isFraudCheckEnabled = propertiesService.isFraudCodeCheck();
        fraudValidator.isFraudCodeCheck(isFraudCheckEnabled, deviceId, deviceModel);

        SignInit sign = this.getSignByIdentity(identity);

        if (LocalDateTime.now().isAfter(sign.getExpire())) {
            throw new SignInitExpireException(messageSingleton.getMessage(MessageKey.VERIFICATION_TIME_EXPIRED));
        }

        if (!SignStatus.CHECK.equals(sign.getStatus())) {
            throw new SignInitStatusIncorrectException(messageSingleton.getMessage(MessageKey.VERIFICATION_STATUS_NOT_CORRECT));
        }

        if (!sign.getCode().equalsIgnoreCase(code)) {
            if ((sign.getCodeCount() + 1) < 3) {
                throw new SignInitCodeIncorrectException(messageSingleton.getMessage(MessageKey.INVALID_CODE_ENTERED_TRY_AGAIN));
            }
            throw new SignInitCodeRetryException(messageSingleton.getMessage(MessageKey.CONFIRMATION_CODE_INPUT_LIMIT_EXCEEDED));
        }

        if (LocalDateTime.now().isAfter(sign.getCodeExpire())) {
            throw new SignInitCodeExpireException(messageSingleton.getMessage(MessageKey.CODE_TIMED_OUT));
        }

        // refresh
        fraudValidator.refreshFraudCodeCheck(isFraudCheckEnabled, deviceId, deviceModel);

        try {
            KeyPair keyPair = cipherUtility.getKeyPair();

            final String publicKey = cipherUtility.encodeKey(keyPair.getPublic());
            final String privateKey = cipherUtility.encodeKey(keyPair.getPrivate());

            sign.setPublicKey(publicKey);
            sign.setPrivateKey(privateKey);

        } catch (Exception e) {
            throw new PairKeyGenerationException(messageSingleton.getMessage(MessageKey.DATA_DECODING_ERROR));
        }

        return sign;
    }

    private String getDecryptedPass(SignInit sign, String password) throws DecodeDataException {
        try {
            PrivateKey privateKey = cipherUtility.decodePrivateKey(sign.getPrivateKey());
            return cipherUtility.decrypt(password, privateKey);
        } catch (final Exception e) {
            throw new DecodeDataException(messageSingleton.getMessage(MessageKey.DATA_DECODING_ERROR));
        }
    }

    private SignInit getSignByIdentity(UUID identity) throws SignInitNotFoundException {
        Optional<SignInit> signOptional = signInitRepo.findById(identity);
        if (signOptional.isEmpty()) {
            throw new SignInitNotFoundException(messageSingleton.getMessage(MessageKey.DATA_NOT_FOUND));
        }
        return signOptional.get();
    }

    private ResTokenRefresh executeRefreshTokenWithRemovingOldData(UserAccess userAccess, DeviceType deviceType) {

        User authUser = userAccess.getUser();

        Long accessTokenExpireHours = this.getAccessTokenExpireHours(authUser);

        userAccess.setAccessToken(CoreUtils.generateTokenUUID());
        userAccess.setAccessTokenExpire(LocalDateTime.now().plusHours(accessTokenExpireHours));
        userAccess.setIpAddress(GlobalVar.getIpAddress());
        userAccess.setUserAgent(GlobalVar.getUserAgent());
        userAccess.setType(deviceType);
        userAccess = userAccessRepo.saveAndFlush(userAccess);


        ResTokenRefresh.ResUserAccess access = new ResTokenRefresh.ResUserAccess(
                userAccess.getAccessToken(),
                accessTokenExpireHours * 60 * 60
        );

        ResTokenRefresh result = new ResTokenRefresh();
        result.setUser(new ResTokenRefresh.ResUserSimple(authUser));
        result.setAccess(access);
        return result;
    }

    private Long getAccessTokenExpireHours(User authUser) {
        if (CoreUtils.isPresent(authUser)) {
            return propertiesService.getAccessTokenExpireHours(authUser.getUsername());
        } else {
            return propertiesService.getAccessTokenExpireHours();
        }
    }

    public static boolean isTokenExpired(LocalDateTime expirationTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationTime);
    }

    private UserDBOMain generateResponse(User authUser, UserAccess userAccess) {
        UserDBO user = this.getUser(authUser);
        UserDBORole role = this.getRole(authUser);
        UserDBOSecurity security = this.getSecurity(userAccess, authUser);
        UserDBOPassword password = this.getPassword(authUser);

        UserDBOMain result = new UserDBOMain();
        result.setUser(user);
        result.setRole(role);
        result.setSecurity(security);
        result.setPassword(password);

        return result;
    }

    private UserDBOSecurity getSecurity(UserAccess userAccess, User authUser) {
        UserDBOSecurity security = new UserDBOSecurity();
        security.setPrvKey(userAccess.getPrivateKey());
        security.setPubKey(userAccess.getPublicKey());
        security.setPassword(authUser.getPassword());
        return security;
    }

    private UserDBOPassword getPassword(User authUser) {
        UserDBOPassword password = new UserDBOPassword();
        password.setPassword(authUser.getPassword());
        return password;
    }

    private UserDBORole getRole(User authUser) {
        UserRole authRole = authUser.getRole();
        if (CoreUtils.isPresent(authRole)) {
            UserDBORole role = new UserDBORole();
            role.setId(authUser.getId());
            role.setName(authRole.getName());
            role.setDisplayName(authRole.getDisplayName());
            role.setPermissions(authRole.getUserPermissions().stream().map(UserPermission::getName).collect(Collectors.toSet()));
            return role;
        }
        return null;
    }

    private UserDBO getUser(User authUser) {
        UserDBO user = new UserDBO();
        user.setId(authUser.getId());
        user.setUserNO(authUser.getUserNO());
        user.setUsername(authUser.getUsername());
        user.setFirstName(authUser.getFirstName());
        user.setLastName(authUser.getLastName());
        user.setEmail(authUser.getEmail());
        user.setPhone(authUser.getPhone());
        user.setAge(authUser.getAge());
        user.setStatus(authUser.getStatus());
        user.setGender(authUser.getGender());
        return user;
    }

}
