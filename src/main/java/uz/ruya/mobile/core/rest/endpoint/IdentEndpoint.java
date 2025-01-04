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
import uz.ruya.mobile.core.config.doc.DocMethodAuth;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqLogin;
import uz.ruya.mobile.core.rest.peyload.req.auth.ReqSignUp;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResEncrypt;
import uz.ruya.mobile.core.rest.peyload.res.auth.ResLogin;

@DocController(name = "Ident Module", description = "Ident Service Endpoint")
@RequestMapping(BaseURI.API1 + BaseURI.IDENT)
public interface IdentEndpoint {

    @DocMethodAuth(
            summary = "Login",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResLogin.class))
    )
    @PostMapping(BaseURI.LOGIN)
    ResponseEntity<?> login(@RequestBody ReqLogin request);

    @DocMethodAuth(
            summary = "Signup",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.SIGNUP)
    ResponseEntity<?> signUp(@RequestBody ReqSignUp request);

    @DocMethodAuth(
            summary = "Get Encrypt Key",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResEncrypt.class))
    )
    @GetMapping(BaseURI.GET + BaseURI.ENCRYPT)
    ResponseEntity<?> getEncryptKey();
}
