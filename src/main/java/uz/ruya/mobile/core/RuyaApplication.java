package uz.ruya.mobile.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("uz.ruya.mobile.core.config.prop")
@OpenAPIDefinition(
        info = @Info(
                title = "ruya-mobile",
                version = "1",
                description = "SUPPER ADMIN",
                contact = @Contact(
                        name = "Asadbek",
                        url = "+998979497771",
                        email = "kushakovasadbek@gmail.com"
                )
        )
)
public class RuyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuyaApplication.class, args);
    }

}
