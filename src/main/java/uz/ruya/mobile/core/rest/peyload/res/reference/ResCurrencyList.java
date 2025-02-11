package uz.ruya.mobile.core.rest.peyload.res.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 2/7/2025 12:07 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResCurrencyList implements Serializable {

    private List<ResCurrency> list = new ArrayList<>();
    private Integer count;

    public ResCurrencyList(List<ResCurrency> list) {
        this.count = list.size();
        this.list = list;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResCurrency implements Serializable {

        private String symbol;
        private String currencyName;
        private String currencyCode;
        private Integer currencyNumber;

    }
}
