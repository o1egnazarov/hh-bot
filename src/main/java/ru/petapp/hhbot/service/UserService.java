package ru.petapp.hhbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petapp.hhbot.repository.model.UserEntity;
import ru.petapp.hhbot.repository.UserRepository;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserByChatId(long chatId) {
        return this.userRepository.getUserEntityById(chatId);
    }

    public Iterable<UserEntity> getUsers() {
        return this.userRepository.findAll();
    }

    public void saveUser(UserEntity user) {
        this.userRepository.save(user);
    }



}
