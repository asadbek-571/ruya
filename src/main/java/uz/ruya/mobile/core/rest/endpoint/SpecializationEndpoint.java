package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethodAuth;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecialization;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecializationParam;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationList;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationParam;

@DocController(name = "Specialization Module", description = "Specialization Endpoint")
@RequestMapping(BaseURI.API1 + BaseURI.SPECIALIZATION)
public interface SpecializationEndpoint {

    @DocMethodAuth(
            summary = "Specialization List",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSpecializationList.class))
    )
    @PostMapping(BaseURI.LIST)
    ResponseEntity<?> specializationList(@RequestBody ReqSpecialization request);

    @DocMethodAuth(
            summary = "Get Specialization Params",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResSpecializationParam.class))
    )
    @PostMapping(BaseURI.PARAM)
    ResponseEntity<?> getParam(@RequestBody ReqSpecializationParam request);
}
