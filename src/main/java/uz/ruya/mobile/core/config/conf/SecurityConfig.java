package uz.ruya.mobile.core.config.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.filter.AfterFilter;
import uz.ruya.mobile.core.config.filter.BeforeFilter;
import uz.ruya.mobile.core.config.handler.AccessHandler;
import uz.ruya.mobile.core.config.handler.AuthHandler;
import uz.ruya.mobile.core.config.prop.AuthProp;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProp authProp;
    private final PasswordEncoder passwordEncoder;
    private final BeforeFilter beforeFilter;
    private final AfterFilter afterFilter;

    private final String[] PERMIT_URLS = new String[]{
            BaseURI.DOC_OPEN_API,
            BaseURI.DOC_SWAGGER_API,
            BaseURI.DOC_SWAGGER_UI,
            "/api/v1/auth/sign/check/user",
            "/api/v1/auth/sign/user/verify",
            "/api/v1/auth/sign/in",
            "/api/v1/auth/sign/up",
            "/api/v1/auth/sign/refresh",
            "/api/v1/auth/sign/code/resend",
            "/api/v1/auth/sign/agreement",
            "/api/v1/auth/sign/encrypt/password"
    };

    static {
        try {
            turnOffSSL();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void turnOffSSL() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
        final var sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new X509TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(PERMIT_URLS)
                .permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling()
                .accessDeniedHandler(new AccessHandler())
                .authenticationEntryPoint(new AuthHandler());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(beforeFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(afterFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        for (AuthProp.User user : authProp.getUsers()) {
            auth.inMemoryAuthentication()
                    .withUser(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(user.getRole());
        }
    }
}
