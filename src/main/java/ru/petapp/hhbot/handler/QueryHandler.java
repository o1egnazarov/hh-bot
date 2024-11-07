package ru.petapp.hhbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.petapp.hhbot.service.manager.QueryManager;

@Log4j2
@Component
@RequiredArgsConstructor
public class QueryHandler implements TelegramHandler {
    private final QueryManager queryManager;

    @Override
    public BotApiMethod<?> answer(Update update) {

        String data = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();


        switch (data) {
            case "letsToWork" -> {
                return this.queryManager.searchVacancy(chatId);
            }
            case "subscribeToVacancy" -> {
                return this.queryManager.subscribeToVacancy(chatId);
            }
            case "unsubscribeToVacancy" -> {
                return this.queryManager.unSubscribeToVacancy(chatId);
            }

            case "getVacancies" -> {
                return this.queryManager.getVacancies(chatId);
            }
        }
        return this.queryManager.collectVacancyParam(chatId, data);

    }


}
