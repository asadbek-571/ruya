package uz.ruya.mobile.core.rest.peyload.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResPaging<T> {

    private List<T> content;

    private ResPagingParams paging;

    public ResPaging(List<T> content, Integer page, Integer size, Long totalElements) {
        ResPagingParams resPagingParams = new ResPagingParams();
        resPagingParams.setPageNumber(page);
        resPagingParams.setTotalPages((int) Math.ceil(totalElements.doubleValue() / size));
        resPagingParams.setTotalItems(totalElements);

        this.content = content;
        this.paging = resPagingParams;
    }

    public ResPaging<T> getDefaultResponse(Integer page) {
        ResPagingParams resPagingParams = new ResPagingParams(page, 0, 0L);
        return new ResPaging<>(new ArrayList<>(), resPagingParams);
    }

    public void setPaging(Integer page, Integer size, Long totalElements) {
        ResPagingParams resPagingParams = new ResPagingParams();
        resPagingParams.setPageNumber(page);
        resPagingParams.setTotalPages((int) Math.ceil(totalElements.doubleValue() / size));
        resPagingParams.setTotalItems(totalElements);

        this.setPaging(resPagingParams);
    }


}
