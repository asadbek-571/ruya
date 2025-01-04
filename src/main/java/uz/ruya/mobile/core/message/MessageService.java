package uz.ruya.mobile.core.message;


import uz.ruya.mobile.core.base.BaseService;
import uz.ruya.mobile.core.config.core.Lang;

public interface MessageService extends BaseService {

    String getMessage(MessageKey messageKey, Lang lang);

}
