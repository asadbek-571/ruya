package uz.ruya.mobile.core.rest.peyload.req.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/10/2025 11:48 AM 
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqSpecialization implements Serializable {

    private Long parentId;

}
