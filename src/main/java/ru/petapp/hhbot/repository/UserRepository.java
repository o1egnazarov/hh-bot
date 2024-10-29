package ru.petapp.hhbot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.petapp.hhbot.entity.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getUserEntityById(long chatId);
}
