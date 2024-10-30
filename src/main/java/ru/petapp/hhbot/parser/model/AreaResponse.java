package ru.petapp.hhbot.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaResponse {
    private List<Area> areas;
}
