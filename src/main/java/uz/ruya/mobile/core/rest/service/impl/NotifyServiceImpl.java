package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.bot.PushBot;
import uz.ruya.mobile.core.rest.service.NotifyService;

/**
 Asadbek Kushakov 1/9/2025 2:02 PM 
 */

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final PushBot pushBot;

    @Override
    public void sendSMS(String username, String signMessage) {
        pushBot.sendOrderNotify(username, signMessage);
    }
}
