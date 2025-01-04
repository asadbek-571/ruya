package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethodAuth;
import uz.ruya.mobile.core.rest.peyload.req.job.ReqAddEmployerAds;

@DocController(name = "Job Module", description = "Job Service Endpoint")
@RequestMapping(BaseURI.API1 + BaseURI.JOB)
public interface JobEndpoint {

    @DocMethodAuth(
            summary = "Add employer ads",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.ADD + BaseURI.EMPLOYER + BaseURI.ADS)
    ResponseEntity<?> addEmployerAds(@RequestBody ReqAddEmployerAds request);
}
