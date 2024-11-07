package ru.petapp.hhbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.petapp.hhbot.service.manager.MessageManger;
import ru.petapp.hhbot.utils.KeyboardFactory;
import ru.petapp.hhbot.service.UserService;
import ru.petapp.hhbot.telegram.bot.BotState;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class MessageHandler implements TelegramHandler {

    private final MessageManger messageManger;

    @Override
    public BotApiMethod<?> answer(Update update) {
        return this.messageManger.fillVacancyParam(update.getMessage());
    }
}
