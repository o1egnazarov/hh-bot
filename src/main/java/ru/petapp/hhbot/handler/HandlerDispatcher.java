package ru.petapp.hhbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Log4j2
@Component
@RequiredArgsConstructor
public class HandlerDispatcher {

    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;
    private final QueryHandler queryHandler;
    private final StickerHandler stickerHandler;

    public BotApiMethod<?> distribute(Update update) {
        if (update.hasCallbackQuery()) {
            return this.queryHandler.answer(update);
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasSticker()) {
                return this.stickerHandler.answer(update);
            }

            if (message.hasText()) {
                String text = message.getText();
                if (text.charAt(0) == '/') {
                    return this.commandHandler.answer(update);

                }
                return this.messageHandler.answer(update);

            }
        }

        log.warn("Unsupported update type {}", update);
        return null;
    }
}
