package ru.petapp.hhbot.service.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.petapp.hhbot.client.HhApiClient;
import ru.petapp.hhbot.parser.AreaParser;
import ru.petapp.hhbot.parser.VacancyParser;
import ru.petapp.hhbot.repository.model.UserEntity;
import ru.petapp.hhbot.service.UserService;
import ru.petapp.hhbot.telegram.bot.BotState;
import ru.petapp.hhbot.utils.KeyboardFactory;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class QueryManager {
    private final UserService userService;
    private final VacancyParser vacancyParser;
    private final AreaParser areaParser;
    private final KeyboardFactory keyboardFactory;
    private final HhApiClient hhApiClient;


    public BotApiMethod<?> searchVacancy(long chatId) {
        var user = userService.getUserByChatId(chatId);

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
    }

    public BotApiMethod<?> subscribeToVacancy(long chatId) {
        var user = userService.getUserByChatId(chatId);
        if (user == null) {
            return SendMessage.builder()
                    .text("Сначала заполните информацию о подходящий именно для вас вакансии." +
                            "Для этого нажмите на \"искать работу\".")
                    .chatId(chatId)
                    .build();
        }
        user.setIsNotify(true);
        this.userService.saveUser(user);
        log.info("User with id {} subscribe to notify", chatId);

        return SendMessage.builder()
                .chatId(chatId)
                .text("Вы успешно подписались на рассылку самых подходящих для вас вакансий!")
                .build();
    }

    public BotApiMethod<?> unSubscribeToVacancy(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Вы успешно отписались от рассылки самых подходящих для вас вакансий!")
                .build();
    }


    public BotApiMethod<?> collectVacancyParam(long chatId, String vacancyParam) {
        var user = this.userService.getUserByChatId(chatId);


        switch (vacancyParam) {
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
        log.error("Unsupported query type {}", vacancyParam);
        return null;
    }

    public BotApiMethod<?> getVacancies(long chatId) {
        var user = this.userService.getUserByChatId(chatId);

        String jsonForArea = this.hhApiClient.getAreasFromApi();
        String areaId = this.areaParser.getAreaId(user.getArea(), jsonForArea);

        String jsonForSearch = this.hhApiClient.getVacancyByUserRequirement(user.getJobTitle(), areaId);

        try {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(vacancyParser.vacancyToString(jsonForSearch))
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
