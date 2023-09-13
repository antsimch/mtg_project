package sg.edu.nus.iss.mtg_server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Service
public class MTGIOApiService {
    
    private static final String MTGIO_API_GENERATE_BOOSTER_PACK = 
            "https://api.magicthegathering.io/v1/sets/";

    public List<String> getBoosterPackFromAPI(String setId) {
        
        String apiUrl = UriComponentsBuilder
                .fromUriString(MTGIO_API_GENERATE_BOOSTER_PACK)
                .pathSegment(setId, "booster")
                .toUriString();

        System.out.println("\n\napiUrl >>>> " + apiUrl + "\n\n");

        RequestEntity<Void> req = RequestEntity.get(apiUrl).build();
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(
                req, String.class);

        List<String> boosterPack = new ArrayList<>();

        JsonArray arr = Json.createReader(new StringReader(resp.getBody()))
                .readObject()
                .getJsonArray("cards");

        boosterPack = arr.stream()
                .map(value -> (JsonObject) value)
                .map(jsonObject -> jsonObject.getString("id"))
                .toList();

        return boosterPack;
    }

    
}
