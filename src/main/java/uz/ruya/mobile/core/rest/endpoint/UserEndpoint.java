package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethod;
import uz.ruya.mobile.core.rest.peyload.res.user.ResProfile;
import uz.ruya.mobile.core.rest.peyload.res.user.ResUser;

@DocController(name = "User Module", description = "User Controller")
@RequestMapping(BaseURI.API1 + BaseURI.USER)
public interface UserEndpoint {

    @DocMethod(
            summary = "User me",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResUser.class))
    )
    @GetMapping(BaseURI.ME)
    ResponseEntity<?> me();

    @DocMethod(
            summary = "Get Profile",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResProfile.class))
    )
    @GetMapping(BaseURI.GET + BaseURI.PROFILE)
    ResponseEntity<?> profile();

}
