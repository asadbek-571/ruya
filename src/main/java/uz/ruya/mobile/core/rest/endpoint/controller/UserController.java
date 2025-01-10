package uz.ruya.mobile.core.rest.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.rest.endpoint.UserEndpoint;

/**
 Asadbek Kushakov 1/10/2025 11:01 AM 
 */

@RestController
public class UserController implements UserEndpoint {

    @Override
    public ResponseEntity<?> me() {
        return GenericResponse.success(20000, "success");
    }
}
