package ru.petapp.hhbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Salary {
    private String from;
    private String to;
}
