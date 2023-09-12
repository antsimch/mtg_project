package sg.edu.nus.iss.mtg_server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.edu.nus.iss.mtg_server.models.Card;
import sg.edu.nus.iss.mtg_server.repositories.CardPoolRepository;

@Service
public class CardPoolService {
    
    private CardPoolRepository cardPoolRepo;

    public CardPoolService(CardPoolRepository cardPoolRepo) {
        this.cardPoolRepo = cardPoolRepo;
    }

    public List<Card> findCardPoolByUserId(String userId) {
        Optional<String> opt = cardPoolRepo.findCardPoolByUserId(userId);
        
        if (opt.isEmpty())
            return new ArrayList<>();

        JsonArray arr = Json.createReader(new StringReader(userId)).readArray();

        List<Card> cards = new ArrayList<>();

        arr.stream()
                .map(jsonObj -> Card.fromJson(jsonObj.toString()))
                .forEach(card -> cards.add(card));

        return cards;
    }

    public boolean saveCardPool(List<Card> cardPool, String userId) {
        cardPoolRepo.saveCardPool(cardPool, userId);

        if (cardPoolRepo.findCardPoolByUserId(userId).isEmpty())
            return false;
        
        return true;
    }
}
