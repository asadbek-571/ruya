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
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.ReqUUID;
import uz.ruya.mobile.core.rest.peyload.req.user.ReqAboutInfo;
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
            summary = "Upload CV",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.UPLOAD + BaseURI.CV)
    ResponseEntity<?> uploadCv(@RequestBody ReqUUID request);

    @DocMethod(
            summary = "Add about me info",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.ADD + BaseURI.ABOUT + BaseURI.INFO)
    ResponseEntity<?> addDescription(@RequestBody ReqAboutInfo request);

    @DocMethod(
            summary = "Toggle Skill",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.TOGGLE + BaseURI.SKILL)
    ResponseEntity<?> toggleSkill(@RequestBody ReqLongId request);


}
