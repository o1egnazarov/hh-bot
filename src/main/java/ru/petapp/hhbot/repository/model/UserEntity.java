package ru.petapp.hhbot.repository.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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


    @Column(name = "is_notify", columnDefinition = "boolean default false")
    private Boolean isNotify = false;

    @NotNull
    @NotEmpty
    @Max(50)
    private String experience;

    @NotNull
    @NotEmpty
    @Max(50)
    private Double salary;

    @NotNull
    @NotEmpty
    @Max(50)
    private String jobTitle;
}
