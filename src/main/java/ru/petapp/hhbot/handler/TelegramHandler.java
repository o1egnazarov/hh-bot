package ru.petapp.hhbot.handler;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramHandler {
    BotApiMethod<?> answer(Update update);
}
