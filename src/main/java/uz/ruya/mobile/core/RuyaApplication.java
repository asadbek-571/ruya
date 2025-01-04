package uz.ruya.mobile.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("uz.ruya.mobile.core.config.prop")
public class RuyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuyaApplication.class, args);
    }

}
