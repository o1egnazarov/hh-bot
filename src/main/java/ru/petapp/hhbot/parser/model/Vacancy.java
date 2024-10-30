package ru.petapp.hhbot.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

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
