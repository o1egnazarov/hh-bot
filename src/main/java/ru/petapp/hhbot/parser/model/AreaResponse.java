package ru.petapp.hhbot.parser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaResponse {
    private String id;
    @JsonProperty("parent_id")
    private String parentId;
    private String name;
    private List<AreaResponse> areas;
}
