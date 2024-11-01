package ru.petapp.hhbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.petapp.hhbot.handler.data.CommandData;
import ru.petapp.hhbot.service.manager.CommandManger;

@Log4j2
@Component
@RequiredArgsConstructor
public class CommandHandler implements TelegramHandler {

    private final CommandManger commandManger;

    @Override
    public BotApiMethod<?> answer(Update update) {

        String command = update.getMessage().getText().substring(1);
        CommandData commandData;

        try {
            commandData = CommandData.valueOf(command);
        } catch (Exception e) {
            log.error("Unsupported command was received: {}", command);
            return this.unknownCommand(update.getMessage().getChatId(), command);
        }

        switch (commandData) {
            case start -> {
                return this.commandManger.startVacancy(update.getMessage());
            }
//            case search -> {
//                return this.commandManger.searchVacancy(update.getMessage().getChatId());
//            }
            case help -> {
                return this.helpAnswer(update.getMessage().getChatId());
            }

        }

        log.warn("Unsupported command type {}", command);
        return this.unknownCommand(update.getMessage().getChatId(), command);
    }


    private BotApiMethod<?> helpAnswer(Long chatId) {
        String commands =
                "Доступные команды:\n" +
                        "\uD83D\uDCBC /start - Начало работы.\n" +
                        "⚙ /settings - Настройка фильтров вакансий.\n" +
                        "\uD83D\uDD0D /search - Запуск поиска новых вакансий по заданным.\n" +
                        "✅ /subscribe - Подписка на уведомления о вакансиях.\n" +
                        "❌ /unsubscribe - Отписаться от уведомлений.\n" +
                        "\uD83D\uDE4F /help - Помощь по командам.\n";

        return SendMessage.builder()
                .chatId(chatId)
                .text(commands)
                .build();
    }

    private BotApiMethod<?> unknownCommand(Long chatId, String command) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Я не слышал ничего про " + command + ". Ознакомтесь со списком команд: /help.")
                .build();
    }
}
