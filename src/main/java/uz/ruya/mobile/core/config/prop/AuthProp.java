package uz.ruya.mobile.core.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth")
public class AuthProp {

    private List<User> users;

    @Data
    public static class User {
        private String username;
        private String password;
        private String role;
    }
}
