package ru.petapp.hhbot.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.petapp.hhbot.parser.model.Area;
import ru.petapp.hhbot.parser.model.AreaResponse;

@Log4j2
@Component
@RequiredArgsConstructor
public class AreaParser {
    private final ObjectMapper objectMapper;

    public String searchAreasId(String json, String name) throws JsonProcessingException {
        var areaResponse = this.objectMapper.readValue(json, AreaResponse.class);

        return searchArea(name, areaResponse);
    }

    private String searchArea(String name, AreaResponse areaResponse) {
        Area area = getAreaResponse(name, areaResponse);
        return area.getId();
    }

    private Area getAreaResponse(String name, AreaResponse areaResponse) {
        for (Area area : areaResponse.getAreas()) {
            if (!area.getName().equalsIgnoreCase(name)) {
                areaResponse.getAreas().removeIf(area1 -> !area1.getName().equalsIgnoreCase(name));
                getAreaResponse(name, areaResponse);
            }
            return area;
        }

        return null;
    }
}
