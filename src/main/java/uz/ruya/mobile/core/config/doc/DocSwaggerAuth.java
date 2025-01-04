package uz.ruya.mobile.core.config.doc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@DocReqHeaderAuth
@DocResError
public @interface DocSwaggerAuth {
}
