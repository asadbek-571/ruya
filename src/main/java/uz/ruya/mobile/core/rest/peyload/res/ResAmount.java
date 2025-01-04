package uz.ruya.mobile.core.rest.peyload.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResAmount implements Serializable {

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("scale")
    private Short scale = 2;

    @JsonProperty("currency")
    private CurrencyType currency = CurrencyType.USD;

    public ResAmount(Long amount) {
        this.amount = CoreUtils.isPresent(amount) ? amount : 0L;
        this.scale = 2;
    }

    public ResAmount(Long amount, CurrencyType currency) {
        this.amount = CoreUtils.isPresent(amount) ? amount : 0L;
        this.scale = 2;
        this.currency = currency;
    }

    public ResAmount(ReqAmount amount) {
        this.amount = amount.getAmount();
        this.currency = amount.getCurrency();
        this.scale = amount.getScale();
    }
}
