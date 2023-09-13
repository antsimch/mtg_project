package sg.edu.nus.iss.mtg_server.repositories;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.models.Card;

@Repository
@AllArgsConstructor
public class CardPoolRepository {
    
    private final RedisTemplate<String, String> template;

    public Optional<String> findCardPoolByDraftId(String draftId) {
        return Optional.ofNullable((template.opsForValue().get(draftId)));
    }

    public void saveCardPool(List<Card> cardPool, String draftId) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        cardPool.stream()
                .map(card -> card.toJsonObjectBuilder())
                .forEach(jsonObj -> arrBuilder.add(jsonObj));

        template.opsForValue().set(
                draftId, 
                arrBuilder.build().toString(),
                Duration.ofHours(1));
    } 
}
