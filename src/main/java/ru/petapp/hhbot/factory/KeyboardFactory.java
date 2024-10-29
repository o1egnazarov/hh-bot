package ru.petapp.hhbot.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class KeyboardFactory {

    public InlineKeyboardMarkup getInlineKeyboard(
            List<String> text,
            List<Integer> configuration,
            List<String> data
    ) {
        if (text.size() != data.size() || text.size() != configuration
                .stream()
                .reduce(0, Integer::sum)
        ) {
            log.error("Wrong arguments: [" + text + "," + data + "," + configuration + "]");
            return null;
        }
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowNumber : configuration) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < rowNumber; i++) {
                var button = InlineKeyboardButton.builder()
                        .text(text.get(index))
                        .callbackData(data.get(index))
                        .build();

                row.add(button);
                index += 1;
            }
            keyboard.add(new InlineKeyboardRow(row));
        }
        return new InlineKeyboardMarkup(keyboard);
    }

}