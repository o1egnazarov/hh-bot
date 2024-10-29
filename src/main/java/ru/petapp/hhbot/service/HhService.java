package ru.petapp.hhbot.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
@Setter
public class HhService {
    @Value("${hh.base-url}")
    private String baseUrl;

    @Value("${hh.header-name}")
    private String headerName;

    @Value("${hh.header-value}")
    private String headerValue;

    public final RestTemplate restTemplate = new RestTemplate();

    public String getVacancyByUserRequirement(String text) {
        var url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/vacancies")
                .queryParam("text", text)
             //   .queryParam("area", area)
                .queryParam("per_page", 5)
                .build()
                .toUriString();

//        var headers = new HttpHeaders();
//        headers.add(headerName, headerValue);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        var response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        log.info( response.getHeaders());

        return restTemplate.getForObject(url, String.class);
    }

    public String getAreasId() {
        var url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/areas")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

}
