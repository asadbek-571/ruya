package uz.ruya.mobile.core.config.doc;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Parameters(
        @Parameter(in = ParameterIn.HEADER, name = "X-Lang", example = "UZB/ENG/RUS/KAA")
)
public @interface DocReqHeader {
}
