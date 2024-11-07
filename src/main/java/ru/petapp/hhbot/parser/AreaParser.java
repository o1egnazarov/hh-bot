package ru.petapp.hhbot.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.petapp.hhbot.parser.model.AreaResponse;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class AreaParser {
    private final ObjectMapper objectMapper;

    public List<AreaResponse> getAreas(String json) {
        try {
            return this.objectMapper.readValue(json, new TypeReference<List<AreaResponse>>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e);
            return null;
        }
    }

    private AreaResponse findByName(List<AreaResponse> parent, String name) {
        for (AreaResponse area : parent) {
            if (area.getName().equalsIgnoreCase(name)) {
                return area;
            }
        }

        for (AreaResponse area : parent) {
            for (AreaResponse child: area.getAreas()){
                AreaResponse result = findByName(child.getAreas(), name);
                if (result != null) {
                    return result;
                }
            }
        }
        log.warn("Invalid area name");
        return null;

//        for (AreaResponse child : parent.getAreas()) {
//            AreaResponse result = findByName(child, name);
//            if (result != null) {
//                return result;
//            }
//        }
    }

    public String getAreaId(String name, String json) {
        List<AreaResponse> areas = this.getAreas(json);
        var areaResponse = this.findByName(areas, name);
        return areaResponse != null ? areaResponse.getId() : null;
    }
}
