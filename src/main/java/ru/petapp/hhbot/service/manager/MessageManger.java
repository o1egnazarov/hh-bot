package ru.petapp.hhbot.service.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.petapp.hhbot.service.UserService;
import ru.petapp.hhbot.telegram.bot.BotState;
import ru.petapp.hhbot.utils.KeyboardFactory;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class MessageManger {
    private final KeyboardFactory keyboardFactory;
    private final UserService userService;

    public BotApiMethod<?> fillVacancyParam(Message message) {
        String messageText = message.getText();
        long chatId = message.getChatId();

        var userEntity = userService.getUserByChatId(message.getChatId());
        BotState state = userEntity.getState();

        switch (state) {
            case ASK_AREA -> {
                userEntity.setArea(messageText);
                userEntity.setState(BotState.ASK_EXPERIENCE);
                this.userService.saveUser(userEntity);
                return this.sendMessage(chatId, "Город: %s введен успешно!".formatted(messageText));
            }
            case ASK_EXPERIENCE -> {
                userEntity.setExperience(messageText);
                userEntity.setState(BotState.ASK_SALARY);
                this.userService.saveUser(userEntity);
                return this.sendMessage(chatId, "Опыт: %s введен успешно!".formatted(messageText));
            }
            case ASK_SALARY -> {
                userEntity.setSalary(Integer.valueOf(messageText));
                userEntity.setState(BotState.ASK_JOB_TITLE);
                this.userService.saveUser(userEntity);
                return this.sendMessage(chatId, "Зарплата: %s введена успешно!".formatted(messageText));
            }
            case ASK_JOB_TITLE -> {
                userEntity.setJobTitle(messageText);
                userEntity.setState(BotState.COMPLETED);
                this.userService.saveUser(userEntity);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text("Почти готово...")
                        .replyMarkup(keyboardFactory.getInlineKeyboard(
                                List.of("Изменить запрос.", "Получить вакансии"),
                                List.of(1, 1),
                                List.of("letsToWork", "getVacancies")
                        ))
                        .build();
            }
        }
        log.warn("Unsupported bot state {}", state);
        throw new UnsupportedOperationException();
    }

    private BotApiMethod<?> sendMessage(long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Город: ", "Зарплата:", "Опыт:", "Название вакансии:"),
                        List.of(2, 2),
                        List.of("city", "salary", "experience", "jobTitle")
                ))
                .build();
    }
}
