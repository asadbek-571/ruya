package uz.ruya.mobile.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.core.Lang;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

   @Override
    public String getMessage(MessageKey key, Lang lang) {
        Optional<Message> messageOptional = messageRepo.findTopByKeyAndLang(key.getKey(), lang);
        if (messageOptional.isPresent()) {
            return messageOptional.get().getMessage();
        }
        return key.getValue();
    }

}
