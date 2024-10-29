package ru.petapp.hhbot.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.petapp.hhbot.entity.UserEntity;
import ru.petapp.hhbot.factory.KeyboardFactory;
import ru.petapp.hhbot.parser.VacancyParser;
import ru.petapp.hhbot.service.HhService;
import ru.petapp.hhbot.service.UserService;
import ru.petapp.hhbot.telegram.bot.BotState;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class QueryHandler implements TelegramHandler {
    private final UserService userService;
    private final KeyboardFactory keyboardFactory;
    private final HhService hhService;
    private final VacancyParser vacancyParser;

    @Override
    public BotApiMethod<?> answer(Update update) {

        String data = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        var user = this.userService.getUserByChatId(chatId);


        switch (data) {
            case "letsToWork" -> {
                return this.letsToWork(chatId, user);
            }
            case "subscribeToWork" -> {
                return this.subscribeToWork(update.getMessage());
            }

            case "getVacancies" -> {
                return this.getVacancies(chatId, user);
            }

            case "city" -> {
                user.setState(BotState.ASK_AREA);
                this.userService.saveUser(user);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text("Введите город:")
                        .build();
            }
            case "salary" -> {
                user.setState(BotState.ASK_SALARY);
                this.userService.saveUser(user);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text("Введите желаемую зарплату:")
                        .build();
            }
            case "experience" -> {
                user.setState(BotState.ASK_EXPERIENCE);
                this.userService.saveUser(user);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text("Введите опыт (в годах):")
                        .build();
            }
            case "jobTitle" -> {
                user.setState(BotState.ASK_JOB_TITLE);
                this.userService.saveUser(user);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text("Введите название вакансии:")
                        .build();
            }
        }

        log.error("Unsupported query type {}", data);
        return null;
    }


    private BotApiMethod<?> letsToWork(long chatId, UserEntity user) {

        if (user == null) {
            user = new UserEntity();
            user.setId(chatId);
            this.userService.saveUser(user);
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text("Заполните следующие поля для поиска работы.")
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Город: ", "Зарплата:", "Опыт:", "Название вакансии:"),
                        List.of(2, 2),
                        List.of("city", "salary", "experience", "jobTitle")
                ))
                .build();

//        UserEntity userEntity = this.userService.getUserByChatId(message.getChatId());
//
//        if (userEntity == null) {
//            userEntity = new UserEntity();
//            userEntity.setId(message.getChatId());
//            userEntity.setState(BotState.ASK_AREA);
//            this.userService.saveUser(userEntity);
//
//            return this.sendMessage(message.getChatId(), "В каком городе ты бы хотел работать?");
//        }


//        BotState state = userEntity.getState();
//
//        switch (state) {
//            case ASK_AREA -> {
//                userEntity.setArea(messageText);
//                userEntity.setState(BotState.ASK_EXPERIENCE);
//                this.userService.saveUser(userEntity);
//                return this.sendMessage(chatId, "Какой у тебя опыт работы?");
//            }
//            case ASK_EXPERIENCE -> {
//                userEntity.setArea(messageText);
//                userEntity.setState(BotState.ASK_SALARY);
//                this.userService.saveUser(userEntity);
//                sendMessage(chatId, "Какую бы хотел зарплату?");
//            }
//            case ASK_SALARY -> {
//                userEntity.setArea(messageText);
//                userEntity.setState(BotState.ASK_JOB_TITLE);
//                this.userService.saveUser(userEntity);
//                sendMessage(chatId, "Напиши название вакансии.");
//            }
//            case ASK_JOB_TITLE -> {
//                userEntity.setArea(messageText);
//                userEntity.setState(BotState.COMPLETED);
//                this.userService.saveUser(userEntity);
//                sendMessage(chatId, "Спасибо за ответы!");
//            }
//        }
//
//
//        return SendMessage.builder()
//                .chatId(chatId)
//                .text(text)
//                .replyMarkup(keyboardFactory.getInlineKeyboard(
//                        List.of("В каком городе ты бы хотел работать?"),
//                        List.of(1),
//                        List.of("city")
//                ))
//                .build();

    }

    private BotApiMethod<?> getVacancies(long chatId, UserEntity user) {
//        String json = this.hhService.getAreasId();
//        String area = this.areaParser.searchAreasId(json, user)

        String json = this.hhService.getVacancyByUserRequirement(user.getJobTitle());

        try {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(vacancyParser.vacancyToString(json))
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private BotApiMethod<?> subscribeToWork(Message message) {
        return SendMessage.builder().build();
    }


}
