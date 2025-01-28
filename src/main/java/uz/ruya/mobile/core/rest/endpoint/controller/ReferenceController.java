package uz.ruya.mobile.core.rest.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.rest.endpoint.ReferenceEndpoint;


@RestController
public class ReferenceController implements ReferenceEndpoint {


    @Override
    public ResponseEntity<?> skillsList(String name) {
        return null;
    }
}
