package uz.ruya.mobile.core.rest.peyload.req.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.peyload.req.ReqPaging;

import java.util.HashMap;
import java.util.Map;

/**
 Asadbek Kushakov 1/6/2025 3:20 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqAnnouncement {

    private Filter filter;
    private ReqPaging paging = new ReqPaging();


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Filter {

        private Long categoryId;
        private Long fromAmount;
        private Long toAmount;
        private CurrencyType currencyType;
        private String title;
        private String fromDate;
        private String toDate;
        private AnnouncementType type;
        Map<String, String> paramFilters = new HashMap<>();

    }


}
