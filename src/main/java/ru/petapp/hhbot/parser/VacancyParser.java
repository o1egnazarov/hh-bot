package ru.petapp.hhbot.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.petapp.hhbot.dto.Vacancy;
import ru.petapp.hhbot.dto.VacancyResponse;

@Log4j2
@Component
@RequiredArgsConstructor
public class VacancyParser {
    private final ObjectMapper objectMapper;

    public String vacancyToString(String jsonResponse) throws JsonProcessingException {
        var vacancyResponse = this.objectMapper.readValue(jsonResponse, VacancyResponse.class);
        StringBuilder stringBuilder = new StringBuilder();

        log.info("Count of vacancy: {}", vacancyResponse.getVacancies().size());

        for (Vacancy vacancy : vacancyResponse.getVacancies()) {

            if (vacancy.getName() != null) {
                stringBuilder.append("Название вакансии: %s.\n".formatted(vacancy.getName()));
            } else {
                stringBuilder.append("Название не указано.\n");
            }

            if (vacancy.getArea() != null) {
                stringBuilder.append("Город: %s.\n".formatted(vacancy.getArea().getName()));
            } else {
                stringBuilder.append("Город не указан.\n");
            }

            if (vacancy.getSalary() != null) {
                stringBuilder.append("Зарплата: от %s до %s.\n".formatted(vacancy.getSalary().getFrom(), vacancy.getSalary().getTo()));
            } else {
                stringBuilder.append("Зарплата не указана.\n");
            }

            if (vacancy.getEmployer() != null) {
                stringBuilder.append("Работодатель: %s.\n".formatted(vacancy.getEmployer().getName()));
            } else {
                stringBuilder.append("Работодатель не указан.\n");
            }

            if (vacancy.getPublishedAt() != null) {
                stringBuilder.append("Дата публикации: %s.\n".formatted(vacancy.getPublishedAt()));
            } else {
                stringBuilder.append("Дата публикации не указана.\n");
            }

            if (vacancy.getUrl() != null) {
                stringBuilder.append("Ссылка на вакансию: %s.\n".formatted(vacancy.getUrl()));
            } else {
                stringBuilder.append("Ссылка не указана.\n");
            }

            if (vacancyResponse.getVacancies().size() > 1) {
                stringBuilder.append("--\n\n");
            }
        }

        log.debug("Final version:\n {}", stringBuilder);

        return stringBuilder.toString();
    }
}
