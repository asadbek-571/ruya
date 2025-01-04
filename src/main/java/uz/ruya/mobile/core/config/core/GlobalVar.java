package uz.ruya.mobile.core.config.core;

import org.apache.logging.log4j.ThreadContext;
import uz.ruya.mobile.core.auth.AuthUser;

import java.util.Objects;
import java.util.UUID;

public class GlobalVar {

    private final static GlobalVar INSTANCE = new GlobalVar();
    private final static ThreadLocal<Long> START_TIME = ThreadLocal.withInitial(System::currentTimeMillis);
    private final static ThreadLocal<String> H_LANG = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_FCM_TOKEN = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_TIMESTAMP = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_IP_ADDRESS = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_REQUEST_ID = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_USER_AGENT = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_DEVICE_ID = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_DEVICE_MODEL = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_DEV_MODE = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_DEVICE_TYPE = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_AUTH_TOKEN = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_OS = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_APP_BUILD = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<String> H_APP_VERSION = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<Boolean> H_DEVICE_ROOT = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private final static ThreadLocal<String> AUTHORIZATION = ThreadLocal.withInitial(String::new);
    private final static ThreadLocal<AuthUser> AUTH_USER = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<UUID> USER_UUID = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<String> USER_ID_STR = ThreadLocal.withInitial(String::new);

    private final static ThreadLocal<Lang> LANG = ThreadLocal.withInitial(() -> Lang.UZB);
    private final static ThreadLocal<DevMode> DEV_MODE = ThreadLocal.withInitial(() -> DevMode.DEFAULT);
    private final static ThreadLocal<DeviceType> DEVICE_TYPE = ThreadLocal.withInitial(() -> DeviceType.UNKNOWN);

    private GlobalVar() {
    }

    public static AuthUser getAuthUser() {
        return GlobalVar.AUTH_USER.get();
    }

    public static void setAuthUser(AuthUser authUser) {
        GlobalVar.AUTH_USER.set(authUser);
    }

    public static UUID getUserUUID() {
        return GlobalVar.USER_UUID.get();
    }

    public static void setUserUuid(UUID userUUID) {
        GlobalVar.USER_UUID.set(userUUID);
    }

    public static String getUserIdStr() {
        return GlobalVar.USER_ID_STR.get();
    }

    public static void setUserIdStr(String userIdStr) {
        GlobalVar.USER_ID_STR.set(userIdStr);
    }

    public static String getLang() {
        return GlobalVar.H_LANG.get();
    }

    public static void setLang(String lang) {
        GlobalVar.H_LANG.set(lang);
    }

    public static String getFcmToken() {
        return GlobalVar.H_FCM_TOKEN.get();
    }

    public static void setFcmToken(String fcmToken) {
        GlobalVar.H_FCM_TOKEN.set(fcmToken);
    }

    public static String getDeviceId() {
        return GlobalVar.H_DEVICE_ID.get();
    }

    public static void setDeviceId(String deviceId) {
        GlobalVar.H_DEVICE_ID.set(deviceId);
    }

    public static String getDeviceModel() {
        return GlobalVar.H_DEVICE_MODEL.get();
    }

    public static void setDeviceModel(String deviceModel) {
        GlobalVar.H_DEVICE_MODEL.set(deviceModel);
    }

    public static String getDevMode() {
        return GlobalVar.H_DEV_MODE.get();
    }

    public static void setDevMode(String devMode) {
        GlobalVar.H_DEV_MODE.set(devMode);
    }

    public static DevMode getDEV_MODE() {
        try {
            return GlobalVar.DEV_MODE.get();
        } catch (Exception e) {
            return DevMode.valueOf(GlobalVar.H_DEV_MODE.get());
        }
    }

    public static void setDEV_MODE(DevMode devMode) {
        GlobalVar.DEV_MODE.set(devMode);
    }

    public static void setDeviceType(String deviceType) {
        GlobalVar.H_DEVICE_TYPE.set(deviceType);
    }

    public static DeviceType getDEVICE_TYPE() {
        try {
            return GlobalVar.DEVICE_TYPE.get();
        } catch (Exception e) {
            return DeviceType.valueOf(GlobalVar.H_DEVICE_TYPE.get());
        }
    }

    public static void setDEVICE_TYPE(DeviceType deviceType) {
        GlobalVar.DEVICE_TYPE.set(deviceType);
    }

    public static Lang getLANG() {
        try {
            if (Objects.isNull(GlobalVar.LANG)) {
                return Lang.UZB;
            }
            return GlobalVar.LANG.get();
        } catch (Exception e) {
            return Lang.valueOf(GlobalVar.H_LANG.get());
        }
    }

    public static void setLANG(Lang lang) {
        GlobalVar.LANG.set(lang);
    }

    public static String getTimestamp() {
        return H_TIMESTAMP.get();
    }

    public static void setTimestamp(String timestamp) {
        GlobalVar.H_TIMESTAMP.set(timestamp);
    }

    public static Long getStartTime() {
        return GlobalVar.START_TIME.get();
    }

    public static void initStartTime() {
        GlobalVar.START_TIME.set(System.currentTimeMillis());
    }

    public static String getIpAddress() {
        return H_IP_ADDRESS.get();
    }

    public static void setIpAddress(String ipAddress) {
        GlobalVar.H_IP_ADDRESS.set(ipAddress);
    }

    public static String getRequestId() {
        return GlobalVar.H_REQUEST_ID.get();
    }

    public static void setRequestId(String requestId) {
        GlobalVar.H_REQUEST_ID.set(requestId);
    }

    public static String getAuthToken() {
        return GlobalVar.H_AUTH_TOKEN.get();
    }

    public static void setAuthToken(String authToken) {
        GlobalVar.H_AUTH_TOKEN.set(authToken);
    }
    public static String getBearerToken() {
        return GlobalVar.AUTHORIZATION.get();
    }

    public static void setBearerToken(String authToken) {
        GlobalVar.AUTHORIZATION.set(authToken);
    }

    public static String getUserAgent() {
        return GlobalVar.H_USER_AGENT.get();
    }

    public static void setUserAgent(String userAgent) {
        GlobalVar.H_USER_AGENT.set(userAgent);
    }

    public static String getOS() {
        return GlobalVar.H_OS.get();
    }

    public static void setOS(String authorization) {
        GlobalVar.H_OS.set(authorization);
    }

    public static String getAppBuild() {
        return GlobalVar.H_APP_BUILD.get();
    }

    public static void setAppBuild(String appVersion) {
        GlobalVar.H_APP_BUILD.set(appVersion);
    }

    public static String getAppVersion() {
        return GlobalVar.H_APP_VERSION.get();
    }

    public static void setAppVersion(String appVersion) {
        GlobalVar.H_APP_VERSION.set(appVersion);
    }

    public static Boolean isDeviceRoot() {
        return GlobalVar.H_DEVICE_ROOT.get();
    }

    public static void setDeviceRoot(Boolean isDeviceRoot) {
        GlobalVar.H_DEVICE_ROOT.set(isDeviceRoot);
    }

    public static void clearContext() {
        // start time
        GlobalVar.START_TIME.remove();
        // header
        GlobalVar.H_LANG.remove();
        GlobalVar.H_FCM_TOKEN.remove();
        GlobalVar.H_TIMESTAMP.remove();
        GlobalVar.H_IP_ADDRESS.remove();
        GlobalVar.H_REQUEST_ID.remove();
        GlobalVar.H_USER_AGENT.remove();
        GlobalVar.H_DEVICE_ID.remove();
        GlobalVar.H_DEVICE_MODEL.remove();
        GlobalVar.H_DEV_MODE.remove();
        GlobalVar.H_DEVICE_TYPE.remove();
        GlobalVar.H_AUTH_TOKEN.remove();
        GlobalVar.H_OS.remove();
        GlobalVar.H_APP_BUILD.remove();
        GlobalVar.H_APP_VERSION.remove();
        GlobalVar.H_DEVICE_ROOT.remove();
        // auth
        GlobalVar.AUTH_USER.remove();
        GlobalVar.USER_UUID.remove();
        GlobalVar.USER_ID_STR.remove();
        // device
        GlobalVar.LANG.remove();
        GlobalVar.DEV_MODE.remove();
        GlobalVar.DEVICE_TYPE.remove();
        // clear log
        ThreadContext.clearAll();
    }
}
