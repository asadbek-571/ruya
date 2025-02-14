package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethod;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqEditProfile;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqSpecialization;
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
            summary = "Attach avatar",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.ATTACH + BaseURI.AVATAR)
    ResponseEntity<?> attachAvatar(@RequestBody ReqSpecialization request);

    @DocMethod(
            summary = "Edit Profile",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.EDIT + BaseURI.PROFILE)
    ResponseEntity<?> editProfile(@RequestBody ReqEditProfile request);
}
