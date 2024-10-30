package ru.petapp.hhbot.telegram;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.petapp.hhbot.handler.HandlerDispatcher;

@Component
@Log4j2
public class HhBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final TelegramProperties telegramProperties;
    private final HandlerDispatcher handlerDispatcher;


    public HhBot(TelegramProperties telegramProperties,
                 HandlerDispatcher handlerDispatcher) {
        this.telegramProperties = telegramProperties;
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.handlerDispatcher = handlerDispatcher;
    }

    @Override
    public String getBotToken() {
        return this.telegramProperties.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        var message = this.handlerDispatcher.distribute(update);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
//    @AfterBotRegistration
//    public void afterRegistration(BotSession botSession) {
//        log.info("Registered bot running state is: " + botSession.isRunning());
//    }
//}
