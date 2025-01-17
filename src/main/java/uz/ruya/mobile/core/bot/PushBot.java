package uz.ruya.mobile.core.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ruya.mobile.core.config.utils.CoreUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class PushBot extends TelegramLongPollingBot {

    public PushBot() {
        super("8058011686:AAFNAApn0Mk-ju2wNG4iNWKTSvXuCtdf7ks1");
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
