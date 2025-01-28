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
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqCategory;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqCategoryParameters;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryList;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryParameters;

@DocController(name = "Announcement Module", description = "Announcement Endpoint")
@RequestMapping(BaseURI.API1 + BaseURI.ANNOUNCEMENT)
public interface AnnouncementEndpoint {

    @DocMethodAuth(
            summary = "Announcement Category List",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResCategoryList.class))
    )
    @PostMapping(BaseURI.GET + BaseURI.CATEGORY)
    ResponseEntity<?> getCategory(@RequestBody ReqCategory request);

    @DocMethodAuth(
            summary = "Get Category Params",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResCategoryParameters.class))
    )
    @PostMapping(BaseURI.GET + BaseURI.CATEGORY + BaseURI.PARAM)
    ResponseEntity<?> getCategoryParam(@RequestBody ReqCategoryParameters request);

    @DocMethodAuth(
            summary = "Add Announcement",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = SuccessMessage.class))
    )
    @PostMapping(BaseURI.ADD)
    ResponseEntity<?> addJobAnnouncement(@RequestBody ReqAddAnnouncement request);

    @DocMethodAuth(
            summary = "Announcement List",
            responseCode = "200",
            description = "Operation success",
            content = @Content(schema = @Schema(implementation = ResAnnouncementOne.class))
    )
    @PostMapping(BaseURI.LIST)
    ResponseEntity<?> announcementPage(@RequestBody ReqAnnouncement request);
}
