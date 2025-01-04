package uz.ruya.mobile.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.Lang;

@Component
@RequiredArgsConstructor
public class MessageSingleton {

    private final MessageService messageService;

    public String getMessage(MessageKey key, Lang lang) {
        return messageService.getMessage(key, lang);
    }

    public String getMessage(MessageKey key) {
        Lang lang = GlobalVar.getLANG();
        return messageService.getMessage(key, lang);
    }
}
