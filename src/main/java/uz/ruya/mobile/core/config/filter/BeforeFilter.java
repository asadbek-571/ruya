package uz.ruya.mobile.core.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import uz.ruya.mobile.core.auth.AuthUser;
import uz.ruya.mobile.core.auth.AuthUserFactory;
import uz.ruya.mobile.core.auth.UserDBO;
import uz.ruya.mobile.core.auth.UserDBOMain;
import uz.ruya.mobile.core.config.core.*;
import uz.ruya.mobile.core.config.excaption.InvalidTokenException;
import uz.ruya.mobile.core.config.excaption.UserBlockedException;
import uz.ruya.mobile.core.config.utils.ConfigUtils;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.service.IdentityService;
import uz.ruya.mobile.core.rest.service.PropertiesService;
import uz.ruya.mobile.core.rest.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class BeforeFilter extends OncePerRequestFilter {

    private final PropertiesService propertiesService;
    private final IdentityService identityService;
    private final UserService profileService;

    private final MessageSingleton messageSingleton;

    private static final List<String> WITHOUT_TOKEN = List.of(
            "/v3/api-docs",
            "/api/v1/auth/sign/check/user",
            "/api/v1/auth/sign/user/verify",
            "/api/v1/auth/sign/in",
            "/api/v1/auth/sign/up",
            "/api/v1/auth/sign/refresh",
            "/api/v1/auth/sign/code/resend",
            "/api/v1/auth/sign/agreement",
            "/api/v1/auth/sign/forget/password",
            "/api/v1/auth/sign/encrypt/password"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        GlobalVar.clearContext();
        GlobalVar.initStartTime();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // check technical break
        if (propertiesService.isTechnicalBreak()) {
            this.exception("Технический перерыв. Приносим свои извинения", 10402, response);
            return;
        }

        String X_IP = request.getHeader("X-Real-IP");
        String X_USER_AGENT = request.getHeader("User-Agent");
        String X_REQUEST_ID = request.getHeader("X-Request-ID");
        String X_TIMESTAMP = request.getHeader("X-Timestamp");
        String X_AUTH_TOKEN = request.getHeader("X-Auth-Token");
        String AUTHORIZATION = request.getHeader("Authorization");

        String X_FCM_TOKEN = request.getHeader("X-Fcm-Token");
        String X_LANG = request.getHeader("X-Lang");
        String X_DEV_MODE = request.getHeader("X-Dev-Mode");
        String X_DEVICE_TYPE = request.getHeader("X-Device-Type");
        String X_APP_VERSION = request.getHeader("X-App-Version");
        String X_APP_BUILD = request.getHeader("X-App-Build");
        String X_DEVICE_MODEL = request.getHeader("X-Device-Model");
        String X_DEVICE_ID = request.getHeader("X-Device-ID");
        String X_OS = request.getHeader("X-OS");
        String X_DEVICE_ROOT = request.getHeader("X-Device-Root");

        Lang lang = Optional.ofNullable(Lang.getByName(X_LANG)).orElse(Lang.UZB);
        DevMode devMode = Optional.ofNullable(DevMode.getByName(X_DEV_MODE)).orElse(DevMode.RELEASE);

        final DeviceType deviceType = DeviceType.getByName(X_DEVICE_TYPE);


        // check app version is active
        if (!DevMode.DEV.equals(devMode) && propertiesService.isCheckAppVersionEnabled()) {
            Integer appVersion = this.getVersion(X_APP_VERSION);
            if (DeviceType.ANDROID.equals(deviceType)) {
                Integer minAndroidVersion = this.getVersion(propertiesService.getMinAndroidVersion());
                if (appVersion != -1 && appVersion < minAndroidVersion) {
                    this.exception(CoreUtils.getMustUpdateMessage(lang), 10401, response);
                    return;
                }
            } else if (DeviceType.IOS.equals(deviceType)) {
                Integer minIosVersion = this.getVersion(propertiesService.getMinIosVersion());
                if (appVersion != -1 && appVersion < minIosVersion) {
                    this.exception(CoreUtils.getMustUpdateMessage(lang), 10401, response);
                    return;
                }
            }
        }

        GlobalVar.setTimestamp(Optional.ofNullable(X_TIMESTAMP).orElse(String.valueOf(System.currentTimeMillis())));
        GlobalVar.setRequestId(Optional.ofNullable(X_REQUEST_ID).orElse(UUID.randomUUID().toString()));
        GlobalVar.setDeviceModel(Optional.ofNullable(X_DEVICE_MODEL).orElse("unknown device"));
        GlobalVar.setDeviceRoot("1".equals(X_DEVICE_ROOT) ? Boolean.TRUE : Boolean.FALSE);
        GlobalVar.setBearerToken(Optional.ofNullable(AUTHORIZATION).orElse(""));
        GlobalVar.setUserAgent(Optional.ofNullable(X_USER_AGENT).orElse(""));
        GlobalVar.setAuthToken(Optional.ofNullable(X_AUTH_TOKEN).orElse(""));
        GlobalVar.setDeviceId(Optional.ofNullable(X_DEVICE_ID).orElse(""));
        GlobalVar.setFcmToken(Optional.ofNullable(X_FCM_TOKEN).orElse(""));
        GlobalVar.setIpAddress(Optional.ofNullable(X_IP).orElse(""));
        GlobalVar.setDeviceType(deviceType.name());
        GlobalVar.setAppVersion(X_APP_VERSION);
        GlobalVar.setDEVICE_TYPE(deviceType);
        GlobalVar.setDevMode(devMode.name());
        GlobalVar.setAppBuild(X_APP_BUILD);
        GlobalVar.setDEV_MODE(devMode);
        GlobalVar.setLang(lang.name());
        GlobalVar.setLANG(lang);
        GlobalVar.setOS(X_OS);

        // check device root
        if (GlobalVar.isDeviceRoot()) {
            this.exception(messageSingleton.getMessage(MessageKey.ROOTED_DEVICE), 20000, response);
            return;
        }

        final String URI = request.getRequestURI();

        Map<String, String> forThreadContextMap = new HashMap<>();
        forThreadContextMap.put("x-correlation-id", GlobalVar.getRequestId());
        forThreadContextMap.put("DEVICE-ID", GlobalVar.getDeviceId());
        forThreadContextMap.put("DEVICE-MODEL", GlobalVar.getDeviceModel());
        forThreadContextMap.put("DEVICE-TYPE", deviceType.name());
        forThreadContextMap.put("URI", URI);
        forThreadContextMap.put("APP-BUILD", X_APP_BUILD);
        forThreadContextMap.put("APP-VERSION", X_APP_VERSION);

        if (!WITHOUT_TOKEN.contains(URI)) {
            if (CoreUtils.isPresent(X_AUTH_TOKEN)) {
                try {

                    UserDBOMain user = identityService.validateToken(X_AUTH_TOKEN);

                    if (CoreUtils.isEmpty(user)) {
                        throw new InvalidTokenException(20402, messageSingleton.getMessage(MessageKey.INVALID_TOKEN));
                    }

                    UserDBO userDBO = user.getUser();

                    final UUID userUUID = userDBO.getId();
                    final String username = userDBO.getUsername();
                    final String email = userDBO.getEmail();

                    UserProfile profile = profileService.getProfile(
                            userUUID,
                            username,
                            email
                    );

                    if (BaseStatus.BLOCKED.equals(profile.getStatus())) {
                        throw new UserBlockedException(20000, messageSingleton.getMessage(MessageKey.ACCOUNT_BLOCKED_FOREVER));
                    }

                    AuthUser authUser = AuthUserFactory.create(
                            username,
                            CoreUtils.isPresent(user.getPassword()) ? user.getPassword().getPassword() : null,
                            user,
                            profile,
                            CoreUtils.isPresent(user.getRole()) ? new HashSet<>(user.getRole().getPermissions()) : new HashSet<>()
                    );

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            authUser, "", authUser.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    GlobalVar.setAuthUser(authUser);
                    GlobalVar.setUserUuid(userUUID);
                    GlobalVar.setUserIdStr(userUUID.toString());

                    forThreadContextMap.put("USERNAME", username);
                    forThreadContextMap.put("USERID", userUUID.toString());

                } catch (InvalidTokenException e) {
                    this.exception(e.getMessage(), e.getCode(), response);
                    return;
                } catch (UserBlockedException e) {
                    this.exception(e.getMessage(), e.getCode(), response);
                    return;
                }
            } else {
                this.exception("Не авторизован", 20400, response);
                return;
            }
        }

        ThreadContext.putAll(forThreadContextMap);

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            this.doFilterWrapped(ConfigUtils.wrapRequest(request), ConfigUtils.wrapResponse(response), filterChain);
        }
    }

    private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    private void exception(String message, Integer code, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        GlobalVar.clearContext();
        response.setStatus(HttpStatus.OK.value());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, GenericResponse.error(code, message).getBody());
        out.flush();
    }

    private Integer getVersion(String appVersion) {
        if (CoreUtils.isEmpty(appVersion)) {
            return -1;
        }
        try {
            return Integer.parseInt(appVersion.split("\\(")[0].replaceAll("\\.", "").trim());
        } catch (Throwable th) {
            return -1;
        }
    }

}