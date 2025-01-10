package uz.ruya.mobile.core.rest.peyload.res.popupAlert;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResPopupAlertList implements Serializable {

    private List<ResPopupAlert> popupAlerts;

}
