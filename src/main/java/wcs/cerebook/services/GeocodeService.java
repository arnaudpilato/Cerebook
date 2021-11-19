package wcs.cerebook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeocodeService {
    private static final String ADDRESS_API_URL = "https://api-adresse.data.gouv.fr/search";
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
                        .queryParam("q", value)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
