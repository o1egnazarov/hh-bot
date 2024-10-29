package ru.petapp.hhbot.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.petapp.hhbot.factory.KeyboardFactory;
import ru.petapp.hhbot.service.handler.data.CommandData;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class CommandHandler implements TelegramHandler {

    private final KeyboardFactory keyboardFactory;

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
                return this.startAnswer(update.getMessage());
            }
            case help -> {
                return this.helpAnswer(update.getMessage().getChatId());
            }
            case subscribe -> {
                return this.subscribeAnswer(update.getMessage());
            }
            case unsubscribe -> {
                return this.unsubscribeAnswer(update.getMessage());
            }
            case search -> {
                return this.searchAnswer(update.getMessage());
            }
        }

        log.warn("Unsupported command type {}", command);
        return this.unknownCommand(update.getMessage().getChatId(), command);
    }

    private SendMessage unsubscribeAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .build();
    }

    private SendMessage subscribeAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .build();
    }

    private SendMessage searchAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .build();
    }

    private BotApiMethod<?> startAnswer(Message message) {

        String text = "Привет, " + message.getFrom().getFirstName() + "! Добро пожаловать в бота для поиска работы мечты.\n" +
                "Более подробно ознакомиться с полным функционалом бота можно здесь: /help.\n" +
                "Приступим?";
        long chatId = message.getChatId();


        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Искать работу мечты.", "Отслеживать избранные вакансии."),
                        List.of(1, 1),
                        List.of("letsToWork", "subscribeToWork")
                ))
                .build();
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
