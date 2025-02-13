package uz.ruya.mobile.core.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.service.PropertiesService;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
public class PushBot extends TelegramLongPollingBot {

    public PushBot(PropertiesService propertiesService) {
        super(propertiesService.getBotToken());
    }

    @Override
    public String getBotUsername() {
        return "ruyauzbot";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendOrderNotify(String username, String text) {

        Map<String, String> adminMap = new HashMap<>();
        adminMap.put("998979497771", "1475328183");
        adminMap.put("998339540690", "1808691792");
        adminMap.put("998900011610", "709127736");
        adminMap.put("998970335536", "2461318");

        if (CoreUtils.isPresent(adminMap.get(username))) {
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(adminMap.get(username));
                sendMessage.setParseMode("Markdown");
                sendMessage.setText(text);
                execute(sendMessage);

            } catch (TelegramApiException ignore) {

            }
        }
    }
}
