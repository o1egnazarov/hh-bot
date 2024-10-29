package ru.petapp.hhbot.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
@Component
public class StickerHandler implements TelegramHandler {

    public BotApiMethod<?> answer(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Хороший стикер, может вернемся к выбору работы мечты?")
                .build();
    }

}
