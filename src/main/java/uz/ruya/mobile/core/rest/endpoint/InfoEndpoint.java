package uz.ruya.mobile.core.rest.endpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ruya.mobile.core.base.BaseURI;
import uz.ruya.mobile.core.config.doc.DocController;
import uz.ruya.mobile.core.config.doc.DocMethod;
import uz.ruya.mobile.core.rest.peyload.res.onboarding.ResOnboardingList;
import uz.ruya.mobile.core.rest.peyload.res.popupAlert.ResPopupAlertList;

@DocController(name = "Info Module", description = "Info Controller")
@RequestMapping(BaseURI.API1 + BaseURI.INFO)
public interface InfoEndpoint {

    @DocMethod(
            summary = "Popup Alert List",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResPopupAlertList.class))
    )
    @GetMapping(BaseURI.POPUP_ALERT + BaseURI.LIST)
    ResponseEntity<?> popupAlertList();

    @DocMethod(
            summary = "Onboarding List",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResOnboardingList.class))
    )
    @GetMapping(BaseURI.ONBOARDING + BaseURI.LIST)
    ResponseEntity<?> onboardingList();

}
