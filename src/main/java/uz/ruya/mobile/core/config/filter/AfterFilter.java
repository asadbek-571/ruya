package uz.ruya.mobile.core.config.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.config.utils.ConfigUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AfterFilter extends OncePerRequestFilter {

    private final Set<String> URLS_NOT_TO_SAVE = new HashSet<>() {{
        add("logs");
        add("swagger");
        add("favicon");
        add("report");
        add("download");
        add("upload");
    }};

    private final Set<String> URLS_NEED_TO_SAVE = new HashSet<>() {{
        // user
        add("/api/v1/ident");
        add("/api/v1/user/profile");
        // one-id
        add("/api/v1/oneid/check");
        // transaction
        add("/api/v1/transaction");
        add("/api/v2/transaction");
        // IMT
        add("/api/v1/international-money-transfer");
        // QR
        add("/api/v1/qr");
        // user-debt
        add("/api/v1/user/debt");
        // card
        add("/api/v1/card");
        add("/api/v1/order/card");
        add("/api/v1/collection/card");
        // wallet
        add("/api/v1/wallet");
        // application
        add("/api/v1/application");
        add("/api/v2/application");
        // auto-payment
        add("/api/v1/auto/payment");
        // insurance-policy
        add("/api/v1/insurance/get/polis");
        // promo-code
        add("/api/v1/promocode");
        // trusted-device
        add("/api/v1/trusted/device");
    }};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(ConfigUtils.wrapRequest(request), ConfigUtils.wrapResponse(response), filterChain);
        }
        GlobalVar.clearContext();
    }

    private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            final String URI = request.getRequestURI();
            if (this.allowToSave(URI)) {
                Logger.reqMain(
                        HttpMethod.valueOf(request.getMethod()),
                        URI,
                        ConfigUtils.getHeaders(request),
                        ConfigUtils.getRequestBody(request),
                        List.of("authorization", "password", "identityToken", "authToken")
                );
                Long endTime = System.currentTimeMillis();
                Logger.resMain(
                        HttpStatus.valueOf(response.getStatus()),
                        (endTime - GlobalVar.getStartTime()),
                        ConfigUtils.getHeaders(response),
                        ConfigUtils.getResponseBody(response),
                        List.of("password", "pubKey", "identityToken", "authToken")
                );
            }
            response.copyBodyToResponse();
        }
    }

    private boolean allowToSave(String URI) {
        for (String url : URLS_NOT_TO_SAVE) {
            if (URI.contains(url))
                return false;
        }
        // added to reduce the total log
        for (String url : URLS_NEED_TO_SAVE) {
            if (URI.contains(url))
                return true;
        }
        return false;
    }
}
