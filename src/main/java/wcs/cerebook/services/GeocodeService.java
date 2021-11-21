package wcs.cerebook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeocodeService {
    private static final String ADDRESS_API_URL = "http://api.positionstack.com/v1/forward";
    private WebClient webClient;

    private WebClient getWebClient() {
        if (webClient == null) {
            webClient = WebClient.create(ADDRESS_API_URL);
        }

        return webClient;
    }

    public JsonNode getAdressAsJson(String value) {
        try {
            return new ObjectMapper().readTree(getAdressAsString(value));
        } catch (JsonProcessingException  e) {
            return null;
        }
    }

    private String getAdressAsString(String value) {
        return getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_key", "e14e0129c7745b8a8e0e70b9316a5d8c")
                        .queryParam("query", value)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
