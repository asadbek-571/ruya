package uz.ruya.mobile.core.config.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.WebRequest;
import uz.ruya.mobile.core.config.core.GenericResult;
import uz.ruya.mobile.core.config.core.GlobalVar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("runner")
    public CommandLineRunner runner() {
        return args -> System.out.printf("START: %s%n", format.format(new Date()));
    }

    @Bean("objectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

                Object status = errorAttributes.get("status");
                Object message = errorAttributes.get("message");

                Map<String, Object> responseEntity = new LinkedHashMap<>();

                responseEntity.put("success", Boolean.FALSE);
                responseEntity.put(
                        "result",
                        new GenericResult<>(
                                Integer.getInteger(String.format("20%s", String.valueOf(status))),
                                String.valueOf(message),
                                GlobalVar.getRequestId(),
                                null
                        )
                );

                return responseEntity;
            }
        };
    }

}
