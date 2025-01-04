package uz.ruya.mobile.core.rest.peyload.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uz.ruya.mobile.core.rest.enums.CurrencyType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReqAmount {

    @Schema(description = "amount", example = "5000")
    private Long amount;
    @Schema(description = "scale example 12300.44", example = "2")
    private Short scale = 2;
    @Schema(description = "currency USD, EUR, UZS, JPY ", example = "USD")
    private CurrencyType currency = CurrencyType.USD;

    public ReqAmount(Long amount) {
        this.amount = amount;
    }

    public ReqAmount(Long amount, CurrencyType currency) {
        this.amount = amount;
        this.currency = currency;
    }

}
