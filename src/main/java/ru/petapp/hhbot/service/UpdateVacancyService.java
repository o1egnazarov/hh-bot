package ru.petapp.hhbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.petapp.hhbot.service.manager.QueryManager;

@Component
@RequiredArgsConstructor
public class UpdateVacancyService {
    private final UserService userService;
    private final QueryManager queryManager;

    @Scheduled(fixedDelay = 43200000)
    public void updateVacancy() {
        var users = this.userService.getUsers();
        for (var user : users) {
            if (user != null && user.getIsNotify()) {
                this.queryManager.getVacancies(user.getId());
            }
        }
    }
}
