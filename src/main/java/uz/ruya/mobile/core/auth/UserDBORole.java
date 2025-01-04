package uz.ruya.mobile.core.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDBORole implements Serializable {

    private UUID id;
    private String name;
    private String displayName;
    private Set<String> permissions;
}
