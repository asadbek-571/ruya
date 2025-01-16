package uz.ruya.mobile.core.bot;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Configuration
public class TelegramBotConfig {
    @Bean
    TelegramBotsApi telegramBotsApi(PushBot bot) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
        return botsApi;
    }
}
