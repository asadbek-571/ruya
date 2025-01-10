package uz.ruya.mobile.core.rest.peyload.req;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReqUUID {
    @NotNull
    private UUID id;
}
