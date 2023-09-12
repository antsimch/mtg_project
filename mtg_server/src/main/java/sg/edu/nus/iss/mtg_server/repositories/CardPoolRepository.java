package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.mtg_server.models.Card;

@Repository
public class CardPoolRepository {
    
    private RedisTemplate<String, String> template;

    public CardPoolRepository(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public Optional<String> findCardPoolByUserId(String userId) {
        return Optional.ofNullable((template.opsForValue().get(userId)));
    }

    public void saveCardPool(List<Card> cardPool, String userId) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        cardPool.stream()
                .map(card -> card.toJsonObjectBuilder())
                .forEach(jsonObj -> arrBuilder.add(jsonObj));

        template.opsForValue().set(
                userId, 
                arrBuilder.build().toString());
    } 
}
