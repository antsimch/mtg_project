package sg.edu.nus.iss.mtg_server.services;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.exceptions.CardNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.DeckNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.UpdateException;
import sg.edu.nus.iss.mtg_server.models.Card;
import sg.edu.nus.iss.mtg_server.models.Deck;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.Util;
import sg.edu.nus.iss.mtg_server.repositories.CardRepository;
import sg.edu.nus.iss.mtg_server.repositories.DeckRepository;
import sg.edu.nus.iss.mtg_server.repositories.MTGRepository;
import sg.edu.nus.iss.mtg_server.repositories.UserRepository;

@Service
@AllArgsConstructor
public class MTGService {

    private final MTGRepository mtgRepo;
    private final DeckRepository deckRepo;
    private final CardRepository cardRepo;
    private final UserRepository userRepo;

    @Transactional(rollbackFor = { UpdateException.class, SQLException.class })
    public void insertDeck(Deck deck) throws UpdateException {

        deck.setDeckId(UUID.randomUUID().toString().substring(0, 8));
        String objectId = deckRepo.insertDeck(
                Document.parse(deck.toJsonObjectBuilder().build().toString()));

        if (objectId == null)
            throw new UpdateException(
                    "An error has occured when saving your deck");

        DeckDetails deckDetails = new DeckDetails(deck.getDeckId(), deck.getUserId());
        System.out.println("\n\n" + deck + "\n\n");
        System.out.println("\n\n" + deckDetails + "\n\n");
        userRepo.updateUserDecks(deck.getUserId(), 1);
        mtgRepo.insertDeckDetails(deckDetails);
    }

    @Transactional(rollbackFor = { UpdateException.class, SQLException.class })
    public void deleteDeckByDeckId(String deckId) throws UpdateException {
        
        Document deleted = deckRepo.deleteDeckByDeckId(deckId);
        if (deleted == null)
            throw new UpdateException(
                    "An error has occured while deleting the deck");

        String userId = deleted.getString("userId");
        userRepo.updateUserDecks(userId, -1);
        mtgRepo.deleteDeckDetailsByDeckId(deckId);
    }

    public Deck findDeckByDeckId(String deckId) throws DeckNotFoundException {
        Document doc = deckRepo.findDeckByDeckId(deckId);

        if (doc == null)
            throw new DeckNotFoundException("Deck not found");

        return Util.parseDocumentToDeck(doc);
    }

    public List<DeckDetails> findDeckDetailsListByUserId(String userId) {
        return mtgRepo.findDeckDetailsListByUserId(userId);
    }

    public List<String> findAllSets() {
        return cardRepo.findAllSets();
    }

    public Card findCardByCardId(String cardId) throws CardNotFoundException {
        
        Document doc = cardRepo.findCardByCardId(cardId);        

        if (doc == null) 
            throw new CardNotFoundException("Card is not found");

        return Util.parseDocumentToCard(doc);
    }
}
