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
import uz.ruya.mobile.core.config.doc.DocMethodAuth;
import uz.ruya.mobile.core.rest.peyload.req.auth.*;
import uz.ruya.mobile.core.rest.peyload.res.auth.*;

@DocController(name = "Ident Module", description = "Ident Service Endpoint")
@RequestMapping(BaseURI.API1 + BaseURI.AUTH + BaseURI.SIGN)
public interface IdentEndpoint {

    @DocMethodAuth(
            summary = "Check User",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSignUserCheck.class))
    )
    @PostMapping(BaseURI.CHECK + BaseURI.USER)
    ResponseEntity<?> signUserCheck(@RequestBody ReqSignUserCheck request);

    @DocMethodAuth(
            summary = "User Verify",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSignUserVerify.class))
    )
    @PostMapping(BaseURI.USER + BaseURI.VERIFY)
    ResponseEntity<?> signUserVerify(@RequestBody ReqSignUserVerify request);

    @DocMethodAuth(
            summary = "Sign In",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSignIn.class))
    )
    @PostMapping(BaseURI.IN)
    ResponseEntity<?> signIn(@RequestBody ReqSignIn request);

    @DocMethodAuth(
            summary = "Sign Up",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSignUp.class))
    )
    @PostMapping(BaseURI.UP)
    ResponseEntity<?> signUp(@RequestBody ReqSignUp request);

    @DocMethod(
            summary = "Sign user (resend SMS)",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSignCodeResend.class))
    )
    @PostMapping(BaseURI.CODE + BaseURI.RESEND)
    ResponseEntity<?> resendCode(@RequestBody ReqSignCodeResend code);

    @DocMethod(
            summary = "Agreement URL",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResAgreementUrl.class))
    )
    @GetMapping(BaseURI.AGREEMENT)
    ResponseEntity<?> agreementURl();


    @DocMethod(
            summary = "front uchunmas!!!",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.ENCRYPT + BaseURI.PASSWORD)
    ResponseEntity<?> encPassword(@RequestBody ReqPassword request);
}
