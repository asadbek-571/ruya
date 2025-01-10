package uz.ruya.mobile.core.rest.peyload.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.ruya.mobile.core.config.utils.CoreUtils;

@Getter
@Setter
@ToString
public class ReqPaging {

    private Integer page;
    private Integer size;

    public Integer getSize() {
        if (CoreUtils.isPresent(this.size)) {
            if (this.size < 10) {
                return 10;
            }
            if (this.size > 100) {
                return 100;
            }
            return this.size;
        }
        return 10;
    }

    public Integer getPage() {
        if (CoreUtils.isPresent(this.page)) {
            return this.page;
        }
        return 0;
    }
}
