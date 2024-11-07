package ru.petapp.hhbot.parser.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyResponse {
    @JsonProperty("items")
    private List<Vacancy> vacancies;
    private Integer found;
    private Integer pages;
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("alternate_url")
    private String url;
}
