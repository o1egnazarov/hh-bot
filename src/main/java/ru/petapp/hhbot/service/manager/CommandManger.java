package ru.petapp.hhbot.service.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.petapp.hhbot.parser.VacancyParser;
import ru.petapp.hhbot.repository.model.UserEntity;
import ru.petapp.hhbot.service.UserService;
import ru.petapp.hhbot.utils.KeyboardFactory;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class CommandManger {
    private final KeyboardFactory keyboardFactory;


    public BotApiMethod<?> startVacancy(Message message) {
        String text =
                "Привет, " + message.getFrom().getFirstName() + "! Добро пожаловать в бота для поиска работы мечты.\n" +
                        "Более подробно ознакомиться с полным функционалом бота можно здесь: /help.\n" +
                        "Приступим?";
        long chatId = message.getChatId();

        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Искать работу мечты.", "Отслеживать избранные вакансии.", "Прекратить отслеживать вакансии."),
                        List.of(1, 1, 1),
                        List.of("letsToWork", "subscribeToVacancy", "unsubscribeToVacancy")
                ))
                .build();
    }

//    public BotApiMethod<?> searchVacancy(long chatId) {
//        var user = userService.getUserByChatId(chatId);
//
//        if (user == null) {
//            user = new UserEntity();
//            user.setId(chatId);
//            this.userService.saveUser(user);
//        }
//
//        return SendMessage.builder()
//                .chatId(chatId)
//                .text("Заполните следующие поля для поиска работы.")
//                .replyMarkup(keyboardFactory.getInlineKeyboard(
//                        List.of("Город: ", "Зарплата:", "Опыт:", "Название вакансии:"),
//                        List.of(2, 2),
//                        List.of("city", "salary", "experience", "jobTitle")
//                ))
//                .build();
//    }
}
