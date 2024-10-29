package ru.petapp.hhbot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.petapp.hhbot.telegram.bot.BotState;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @NotNull
    private Long id;

    private BotState state;
    @NotNull
    @NotEmpty
    @Max(50)
    private String area;

    @NotNull
    @NotEmpty
    @Max(50)
    private String experience;

    @NotNull
    @NotEmpty
    @Max(50)
    private Integer salary;

    @NotNull
    @NotEmpty
    @Max(50)
    private String jobTitle;
}
