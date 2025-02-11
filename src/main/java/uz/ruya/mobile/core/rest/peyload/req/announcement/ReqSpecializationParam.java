package uz.ruya.mobile.core.rest.peyload.req.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/25/2025 7:40 PM 
 */
@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqSpecializationParam implements Serializable {

    private Long categoryId;

}
