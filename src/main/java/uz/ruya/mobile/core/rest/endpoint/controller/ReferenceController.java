package uz.ruya.mobile.core.rest.endpoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.ruya.mobile.core.config.core.GenericResponse;
import uz.ruya.mobile.core.config.logger.Logger;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.endpoint.ReferenceEndpoint;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResCurrencyList;
import uz.ruya.mobile.core.rest.service.ReferenceService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ReferenceController implements ReferenceEndpoint {

    private final MessageSingleton messageSingleton;
    private final ReferenceService service;


    @Override
    public ResponseEntity<?> skillsList(String name) {
        return null;
    }

    @Override
    public ResponseEntity<?> currencyList() {
        try {
            List<ResCurrencyList.ResCurrency> list = new ArrayList<>();
            for (CurrencyType value : CurrencyType.values()) {
                ResCurrencyList.ResCurrency currency = new ResCurrencyList.ResCurrency();
                currency.setCurrencyNumber(value.getCurrencyNumber());
                currency.setCurrencyName(value.getCurrencyName());
                currency.setCurrencyCode(value.getCurrencyCode());
                currency.setSymbol(value.getSymbol());
                list.add(currency);
            }
            return GenericResponse.success(40000, "Success", new ResCurrencyList(list));
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }

    @Override
    public ResponseEntity<?> addressList(ReqLongId request) {
        try {
            var result = service.addressList(request);
            return GenericResponse.success(40000, "Success", result);
        } catch (Throwable th) {
            Logger.error(th);
            return GenericResponse.error(20000, messageSingleton.getMessage(MessageKey.UNKNOWN_ERROR));
        }
    }


}
