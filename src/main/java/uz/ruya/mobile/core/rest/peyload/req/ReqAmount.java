package uz.ruya.mobile.core.rest.peyload.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uz.ruya.mobile.core.rest.enums.CurrencyType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqAmount {

    private Long amount;
    private Short scale = 2;
    private CurrencyType currency = CurrencyType.USD;

    public ReqAmount(Long amount) {
        this.amount = amount;
    }

    public ReqAmount(Long amount, CurrencyType currency) {
        this.amount = amount;
        this.currency = currency;
    }

}
