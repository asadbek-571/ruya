package uz.ruya.mobile.core.rest.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.rest.endpoint.UserEndpoint;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.ReqUUID;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqAboutInfo;

/**
 Asadbek Kushakov 1/10/2025 11:01 AM 
 */

@RestController
public class UserController implements UserEndpoint {

    @Override
    public ResponseEntity<?> me() {
        return GenericResponse.success(20000, "success");
    }

    @Override
    public ResponseEntity<?> uploadCv(ReqUUID request) {
        return null;
    }

    @Override
    public ResponseEntity<?> addDescription(ReqAboutInfo request) {
        return null;
    }

    @Override
    public ResponseEntity<?> toggleSkill(ReqLongId request) {
        return null;
    }
}
