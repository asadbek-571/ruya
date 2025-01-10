package uz.ruya.mobile.core.rest.peyload.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ReqLongId {
    @NotNull
    private Long id;
}
