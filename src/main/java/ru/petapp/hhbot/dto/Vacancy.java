package ru.petapp.hhbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacancy {
    private String id;
    private String name;
    private Area area;
    private Salary salary;
    private Employer employer;
    @JsonProperty("published_at")
    private String publishedAt;
    @JsonProperty("alternate_url")
    private String url;
}
