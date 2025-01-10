package uz.ruya.mobile.core.rest.peyload.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResPagingParams {

    private Integer pageNumber;

    private Integer totalPages;

    private Long totalItems;

}
