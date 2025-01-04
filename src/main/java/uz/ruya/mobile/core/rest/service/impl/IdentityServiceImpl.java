package uz.ruya.mobile.core.rest.service.impl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.auth.*;
import uz.ruya.mobile.core.config.core.CipherUtility;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.excaption.*;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.user.*;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.SignStatus;
import uz.ruya.mobile.core.rest.enums.UserRoleType;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqLogin;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqSignUp;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResEncrypt;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResLogin;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResSignUp;
import uz.ruya.mobile.core.rest.repo.auth.SignInitRepo;
import uz.ruya.mobile.core.rest.repo.auth.UserAccessRepo;
import uz.ruya.mobile.core.rest.repo.auth.UserRepo;
import uz.ruya.mobile.core.rest.repo.auth.UserRoleRepo;
import uz.ruya.mobile.core.rest.service.IdentityService;

import javax.management.relation.RoleNotFoundException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 Asadbek Kushakov 12/25/2024 10:46 AM 
 */

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.secret.access-time}")
    private Long expirationAccessTime;

    @Value("${jwt.secret.refresh-time}")
    private Long expirationRefreshTime;

    private final UserAccessRepo userAccessRepo;
    private final UserRoleRepo roleRepo;
    private final UserRepo userRepo;

    private final CipherUtility cipherUtility;
    private final PasswordEncoder passwordEncoder;

    private final MessageSingleton messageSingleton;
    private final SignInitRepo signInitRepo;

    @Override
    public UserDBOMain validateToken(String authorization) throws InvalidTokenException {
        try {

            UUID userId = extractUserId(authorization);

            if (!checkValidateToken(authorization)) {

                UserAccess userAccess = getUserAccess(userId);
                User authUser = userAccess.getUser();

                if (CoreUtils.isEmpty(authUser) || !BaseStatus.ACTIVE.equals(authUser.getStatus())) {
                    throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
                }

                userAccess.setAccessToken(generateToken(authUser.getId()));
                userAccess.setAccessTokenExpire(LocalDateTime.now().plusSeconds(expirationAccessTime));
                userAccessRepo.save(userAccess);

                return generateResponse(authUser, userAccess);

            } else {

                UserAccess userAccess = getUserAccess(userId);
                User authUser = userAccess.getUser();

                if (CoreUtils.isEmpty(authUser) || !BaseStatus.ACTIVE.equals(authUser.getStatus())) {
                    throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
                }

                return generateResponse(authUser, userAccess);

            }
        } catch (Throwable th) {
            throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
        }
    }

    @Override
    public ResLogin login(ReqLogin request) throws EntityNotFoundException, DecodeDataException, SignInitPasswordValidationException, RoleNotFoundException {

        UserRole userRole = getUserRole(request.getUserRoleType());

        Optional<User> optionalUser = userRepo.findByEmailAndRole(request.getUsername(), userRole);

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(messageSingleton.getMessage(MessageKey.USER_NOT_FOUND));
        }

        User user = optionalUser.get();
        UserAccess userAccess = user.getAccess();

        final String pass = this.getDecryptedPass(userAccess.getPrivateKey(), request.getPassword());

        if (!CoreUtils.checkPassword(pass) || !passwordEncoder.matches(pass, user.getPassword())) {
            throw new SignInitPasswordValidationException(messageSingleton.getMessage(MessageKey.INCORRECT_PASSWORD));
        }

        String accessToken = generateToken(user.getId());

        userAccess.setAccessToken(accessToken);
        userAccess.setAccessTokenExpire(LocalDateTime.now().plusSeconds(expirationAccessTime));
        userAccess.setRefreshToken(UUID.randomUUID());
        userAccess.setRefreshTokenExpire(LocalDateTime.now().plusSeconds(expirationRefreshTime));
        userAccess.setType(GlobalVar.getDEVICE_TYPE());
        userAccess.setUserAgent(GlobalVar.getUserAgent());
        userAccess.setIpAddress(GlobalVar.getIpAddress());
        userAccess = userAccessRepo.save(userAccess);

        return new ResLogin(userAccess.getAccessToken());
    }

    @Override
    public ResSignUp signUp(ReqSignUp request) throws UserExistException, RoleNotFoundException, DecodeDataException, SignInitPasswordValidationException, EntityNotFoundException {

        SignInit sign = signInitRepo.findByIpAndDeviceId(GlobalVar.getIpAddress(), GlobalVar.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.SESSION_NOT_FOUND)));

        UserRole userRole = getUserRole(request.getUserRoleType());

        Optional<User> optionalUser = userRepo.findByEmailAndRole(request.getUsername(), userRole);

        if (optionalUser.isPresent()) {
            throw new UserExistException(messageSingleton.getMessage(MessageKey.USER_ALREADY_EXISTS));
        }

        final String pass = this.getDecryptedPass(sign.getPrivateKey(), request.getPassword());

        if (!CoreUtils.checkPassword(pass)) {
            throw new SignInitPasswordValidationException(messageSingleton.getMessage(MessageKey.INCORRECT_PASSWORD_FORMAT));
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(pass));
        user.setUsername(request.getUsername());
        user.setEmail(request.getUsername());
        user.setStatus(BaseStatus.ACTIVE);
        user.setRole(userRole);
        user = userRepo.saveAndFlush(user);

        UserAccess userAccess = new UserAccess();
        userAccess.setPrivateKey(sign.getPrivateKey());
        userAccess.setPublicKey(sign.getPublicKey());
        userAccess.setUser(user);
        userAccessRepo.save(userAccess);

        sign.setIsReg(true);
        sign.setStatus(SignStatus.VERIFIED);
        sign.setUsername(request.getUsername());
        sign.setUserUUID(user.getId());
        sign.setStatusMessage("Successfully registered");
        signInitRepo.save(sign);

        var signUp = new ResSignUp();
        signUp.setMessage(messageSingleton.getMessage(MessageKey.USER_SUCCESSFULLY_REGISTERED));
        signUp.setIsReg(true);
        return signUp;
    }

    @Override
    public ResEncrypt getEncryptKey() throws PairKeyGenerationException {
        try {

            Optional<SignInit> optionalSignInit = signInitRepo.findByIpAndDeviceId(GlobalVar.getIpAddress(), GlobalVar.getDeviceId());
            SignInit sign;

            if (optionalSignInit.isPresent()) {
                sign = optionalSignInit.get();

                if (LocalDateTime.now().isAfter(sign.getExpire())) {
                    sign = updateSignInitWithNewKeyPair(sign);
                }
            } else {
                sign = createNewSignInit();
            }

            return new ResEncrypt(sign.getPublicKey());

        } catch (Exception e) {
            throw new PairKeyGenerationException(messageSingleton.getMessage(MessageKey.DATA_DECODING_ERROR));
        }

    }

    private UserRole getUserRole(UserRoleType request) throws RoleNotFoundException {
        return roleRepo.findFirstByType(request)
                .orElseThrow(() -> new RoleNotFoundException(messageSingleton.getMessage(MessageKey.ROLE_NOT_FOUND)));
    }

    private UserAccess getUserAccess(UUID userId) throws InvalidTokenException {
        return userAccessRepo.findByUserId(userId).orElseThrow(() ->
                new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN)));
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

    private SignInit updateSignInitWithNewKeyPair(SignInit sign) {
        KeyPair keyPair = cipherUtility.getKeyPair();

        final String publicKey = cipherUtility.encodeKey(keyPair.getPublic());
        final String privateKey = cipherUtility.encodeKey(keyPair.getPrivate());

        sign.setCodeExpire(DateUtils.codeExpire());
        sign.setDevMode(GlobalVar.getDEV_MODE());
        sign.setAgent(GlobalVar.getUserAgent());
        sign.setIp(GlobalVar.getIpAddress());
        sign.setPrivateKey(privateKey);
        sign.setPublicKey(publicKey);

        return signInitRepo.saveAndFlush(sign);
    }

    private SignInit createNewSignInit() {
        KeyPair keyPair = cipherUtility.getKeyPair();

        final String publicKey = cipherUtility.encodeKey(keyPair.getPublic());
        final String privateKey = cipherUtility.encodeKey(keyPair.getPrivate());

        SignInit sign = new SignInit();
        sign.setCodeExpire(DateUtils.codeExpire());
        sign.setDeviceId(GlobalVar.getDeviceId());
        sign.setDevMode(GlobalVar.getDEV_MODE());
        sign.setAgent(GlobalVar.getUserAgent());
        sign.setExpire(DateUtils.codeExpire());
        sign.setIp(GlobalVar.getIpAddress());
        sign.setPrivateKey(privateKey);
        sign.setPublicKey(publicKey);

        return signInitRepo.saveAndFlush(sign);
    }

    private String getDecryptedPass(String privateKeyStr, String password) throws DecodeDataException {
        try {
            PrivateKey privateKey = cipherUtility.decodePrivateKey(privateKeyStr);
            return cipherUtility.decrypt(password, privateKey);
        } catch (final Exception e) {
            throw new DecodeDataException(messageSingleton.getMessage(MessageKey.DATA_DECODING_ERROR));
        }
    }

    private String getEncryptedPass(UserAccess userAccess, String password) throws DecodeDataException {
        try {
            PublicKey privateKey = cipherUtility.decodePublicKey(userAccess.getPublicKey());
            return cipherUtility.encrypt(password, privateKey);
        } catch (final Exception e) {
            throw new DecodeDataException(messageSingleton.getMessage(MessageKey.DATA_DECODING_ERROR));
        }
    }

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationAccessTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean checkValidateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public UUID extractUserId(String token) throws InvalidTokenException {
        try {
            String userIdStr = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return UUID.fromString(userIdStr);

        } catch (Exception e) {
            throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
        }
    }
}
