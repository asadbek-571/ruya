package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethod;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResAddressList;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResCurrencyList;
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

    @DocMethod(
            summary = "Get Currency list",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResCurrencyList.class))
    )
    @GetMapping(BaseURI.CURRENCY + BaseURI.LIST)
    ResponseEntity<?> currencyList();


    @DocMethod(
            summary = "Get Address list",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResAddressList.class))
    )
    @PostMapping(BaseURI.ADDRESS + BaseURI.LIST)
    ResponseEntity<?> addressList(@RequestBody ReqLongId request);

}
