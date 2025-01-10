package uz.ruya.mobile.core.rest.peyload.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResImg implements Serializable {

    private String contentType;
    private String path;
    private String name;
    private String ext;
    private String suffix;
    private List<String> extraSuffix = List.of(
            "X24", "X32", "X64", "X128", "X256", "X512"
    );
}
