package ru.petapp.hhbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petapp.hhbot.entity.UserEntity;
import ru.petapp.hhbot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserByChatId(long chatId) {
        var user = this.userRepository.getUserEntityById(chatId);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        return user;
    }

    public void saveUser(UserEntity user) {
        this.userRepository.save(user);
    }

}
