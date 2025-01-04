package uz.ruya.mobile.core.config.logger;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.Service;


import java.util.List;

@Log4j2
public class Logger {

    private static final Service defaultService = Service.CORE;
    private static final String changeValue = "******";

    private static final String reqFormat = "[user: %s] : [log: %s] : [service: %s(%s)] : [endpoint: %s - %s] : [header: %s] : [body: %s]";
    // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [METHOD - URL] : [HEADERS] : [BODY]

    private static final String resFormat = "[user: %s] : [log: %s] : [service: %s(%s)] : [status: %s (%sms)] : [header: %s] : [body: %s]";
    // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [STATUS (TIME)] : [HEADERS] : [BODY]

    private static final String excFormat = "[user: %s] : [log: %s] : [message: %s]";
    // [USER-ID] : [LOG-ID] : [MESSAGE]


    public static void reqMain(HttpMethod method, String url, String headers, String body) {
        reqInfo(defaultService, method, url, headers, body, List.of());
    }

    public static void reqMain(HttpMethod method, String url, String headers, String body, List<String> keys) {
        reqInfo(defaultService, method, url, headers, body, keys);
    }

    public static void reqService(Service service, HttpMethod method, String url, String headers, String body) {
        reqInfo(service, method, url, headers, body, List.of());
    }

    public static void reqService(Service service, HttpMethod method, String url, String headers, String body, String requestId, String userId) {
        reqInfo(service, method, url, headers, body, requestId, userId, List.of());
    }


    public static void reqService(Service service, HttpMethod method, String url, String headers, String body, List<String> keys) {
        reqInfo(service, method, url, headers, body, keys);
    }

    public static void resMain(HttpStatus status, Long time, String headers, String body) {
        resInfo(defaultService, status, time, headers, body, List.of());
    }

    public static void resMain(HttpStatus status, Long time, String headers, String body, List<String> keys) {
        resInfo(defaultService, status, time, headers, body, keys);
    }

    public static void resService(Service service, HttpStatus status, Long time, String headers, String body) {
        resInfo(service, status, time, headers, body, List.of());
    }

    public static void resService(Service service, HttpStatus status, Long time, String headers, String body, String requestId, String userId) {
        resInfo(service, status, time, headers, body, requestId, userId, List.of());
    }

    public static void resService(Service service, HttpStatus status, Long time, String headers, String body, List<String> keys) {
        resInfo(service, status, time, headers, body, keys);
    }

    public static void error(Exception e) {
        exception(e);
    }

    public static void error(String message, Exception e) {
        exception(message, e);
    }

    public static void error(Throwable e) {
        exception(e);
    }

    public static void error(String message, Throwable e) {
        exception(message, e);
    }

    public static void info(String s) {
        log.info(s);
    }

    private static void reqInfo(Service service, HttpMethod method, String url, String headers, String body, List<String> keys) {
        // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [METHOD - URL] : [HEADERS] : [BODY]
        log.info(
                String.format(
                        reqFormat,
                        GlobalVar.getUserUUID(),
                        GlobalVar.getRequestId(),
                        service.name().toUpperCase(),
                        LogType.REQ.name().toUpperCase(),
                        method.name(),
                        url,
                        keys.isEmpty() ? headers : clearLog(headers, keys),
                        keys.isEmpty() ? body : clearLog(body, keys)
                )
        );
    }

    private static void reqInfo(Service service, HttpMethod method, String url, String headers, String body, String requestId, String userId, List<String> keys) {
        // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [METHOD - URL] : [HEADERS] : [BODY]
        log.info(
                String.format(
                        reqFormat,
                        userId,
                        requestId,
                        service.name().toUpperCase(),
                        LogType.REQ.name().toUpperCase(),
                        method.name(),
                        url,
                        keys.isEmpty() ? headers : clearLog(headers, keys),
                        keys.isEmpty() ? body : clearLog(body, keys)
                )
        );
    }

    private static void resInfo(Service service, HttpStatus status, Long time, String headers, String body, List<String> keys) {
        // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [STATUS (TIME)] : [HEADERS] : [BODY]
        log.info(
                String.format(
                        resFormat,
                        GlobalVar.getUserUUID(),
                        GlobalVar.getRequestId(),
                        service.name().toUpperCase(),
                        LogType.RES.name().toUpperCase(),
                        status.value(),
                        time,
                        keys.isEmpty() ? headers : clearLog(headers, keys),
                        keys.isEmpty() ? body : clearLog(body, keys)
                )
        );
    }

    private static void resInfo(Service service, HttpStatus status, Long time, String headers, String body, String requestId, String userId, List<String> keys) {
        // [USER-ID] : [LOG-ID] : [SERVICE (TYPE)] : [STATUS (TIME)] : [HEADERS] : [BODY]
        log.info(
                String.format(
                        resFormat,
                        userId,
                        requestId,
                        service.name().toUpperCase(),
                        LogType.RES.name().toUpperCase(),
                        status.value(),
                        time,
                        keys.isEmpty() ? headers : clearLog(headers, keys),
                        keys.isEmpty() ? body : clearLog(body, keys)
                )
        );
    }

    private static void exception(Exception e) {
        // [USER-ID] : [LOG-ID] : [MESSAGE]
        log.error(
                String.format(
                        excFormat,
                        GlobalVar.getAuthToken(),
                        GlobalVar.getRequestId(),
                        e.getMessage()
                ),
                e
        );
    }

    private static void exception(String message, Exception e) {
        // [USER-ID] : [LOG-ID] : [MESSAGE]
        log.error(
                String.format(
                        excFormat,
                        GlobalVar.getAuthToken(),
                        GlobalVar.getRequestId(),
                        message
                ),
                e
        );
    }

    private static void exception(Throwable e) {
        // [USER-ID] : [LOG-ID] : [MESSAGE]
        log.error(
                String.format(
                        excFormat,
                        GlobalVar.getAuthToken(),
                        GlobalVar.getRequestId(),
                        e.getMessage()
                ),
                e
        );
    }

    private static void exception(String message, Throwable e) {
        // [USER-ID] : [LOG-ID] : [MESSAGE]
        log.error(
                String.format(
                        excFormat,
                        GlobalVar.getAuthToken(),
                        GlobalVar.getRequestId(),
                        message
                ),
                e
        );
    }

    private static String clearLog(String jsonStr, List<String> keys) {
        String result = jsonStr;
        for (String key : keys) {
            result = result.replaceAll("(\\s*?\"" + key + "\"\\s*?:)(\\s*?\".*?\"\\s*?,)", "$1\"s:" + Logger.changeValue + "\",");
            //result = result.replaceAll("(\\s*?\"" + key + "\"\\s*?:)(\\s*?\".*?\"\\s*?})", "$1\"" + Logger.changeValue + "\"}");
            //result = result.replaceAll("(\\s*?\"" + key + "\"\\s*?:)(\\s*?\\d*?\\.?\\d*?\\s*?,)", "$1\"d:" + Logger.changeValue + "\",");
        }
        return result;
    }
}
