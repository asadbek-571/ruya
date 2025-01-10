package uz.ruya.mobile.core.rest.peyload.res.popupAlert;

import lombok.*;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlert;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResPopupAlert implements Serializable {

    private UUID uuid;

    private String title;

    private String description;

    private ResImg image;

    public ResPopupAlert(PopupAlert popupAlert) {
        this.uuid = popupAlert.getUuid();
        this.title = popupAlert.getTitle();
        this.description = popupAlert.getDescription();
        this.image = FileUtils.getPopUpAlertImage(popupAlert.getImage());
    }

}
