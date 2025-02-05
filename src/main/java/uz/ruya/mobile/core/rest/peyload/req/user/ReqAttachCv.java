package uz.ruya.mobile.core.rest.peyload.req.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 Asadbek Kushakov 1/28/2025 2:45 PM 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqAttachCv implements Serializable {

    private UUID attachmentId;
    private Boolean isMainCv;

}
