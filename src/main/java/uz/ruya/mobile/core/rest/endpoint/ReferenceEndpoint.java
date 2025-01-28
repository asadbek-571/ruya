package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethod;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResReference;

@DocController(name = "Reference Module", description = "Reference Controller")
@RequestMapping(BaseURI.API1 + BaseURI.REFERENCE)
public interface ReferenceEndpoint {

    @DocMethod(
            summary = "Skills list",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResReference.class))
    )
    @GetMapping(BaseURI.SKILL + BaseURI.LIST + "/{name}")
    ResponseEntity<?> skillsList(@PathVariable(name = "name") String name);

}
