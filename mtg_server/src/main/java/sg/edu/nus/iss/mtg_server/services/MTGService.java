package sg.edu.nus.iss.mtg_server.services;

import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.exceptions.CardNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.DeckNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.FailedToSaveDeckException;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidUsernameOrPasswordException;
import sg.edu.nus.iss.mtg_server.exceptions.UsernameAlreadyTakenException;
import sg.edu.nus.iss.mtg_server.models.Card;
import sg.edu.nus.iss.mtg_server.models.Deck;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.DraftDetails;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.models.Util;
import sg.edu.nus.iss.mtg_server.repositories.CardRepository;
import sg.edu.nus.iss.mtg_server.repositories.DeckRepository;
import sg.edu.nus.iss.mtg_server.repositories.MTGRepository;

@Service
@AllArgsConstructor
public class MTGService {

    private final MTGRepository mtgRepo;
    private final DeckRepository deckRepo;
    private final CardRepository cardRepo;

    /*
     * Service for creation of new user, checks for duplicate username in
     * database and throws custom UsernameAlreadyTakenException if database
     * already contains the same username
     */
    public void insertUser(User user) throws UsernameAlreadyTakenException {
        User result = mtgRepo.findUser(user.getUsername());

        if (result != null)
            throw new UsernameAlreadyTakenException(
                    "Username is already taken");

        user.setUserId(UUID.randomUUID().toString().substring(0, 16));
        System.out.println("\n\nuserId >>>> " + user.getUserId() + "\n\n");
        mtgRepo.insertUser(user);
    }

    /*
     * Authentication of user for login
     * TODO: Hashing and JWT implementation
     */
    public String authenticate(
            String username,
            String password) throws InvalidUsernameOrPasswordException {
        User result = mtgRepo.findUser(username);

        if (result == null) 
            throw new InvalidUsernameOrPasswordException(
                    "Invalid username and/or password");

        return result.getUserId();
    }

    public void insertDeck(Deck deck) throws FailedToSaveDeckException {
        deck.setDeckId(UUID.randomUUID().toString().substring(0, 16));
        String objectId = deckRepo.insertDeck(Util.parseDeckToDocument(deck));

        if (objectId == null)
            throw new FailedToSaveDeckException(
                    "An error has occured when saving your deck");
    }

    public Deck findDeckByDeckId(String deckId) throws DeckNotFoundException {
        Document doc = deckRepo.findDeckByDeckId(deckId);

        if (doc == null)
            throw new DeckNotFoundException("Deck not found");

        return Util.parseDocumentToDeck(doc);
    }

    // public List<DeckDetails> findDeckDetailsListByUserId(String userId) {
    //     return mtgRepo.findDeckDetailsListByUserId(userId);
    // }

    // public List<DraftDetails> findDraftDetailsListByUserId(String userId) {
    //     return mtgRepo.findDraftDetailsListByUserId(userId);
    // }

    public List<DraftDetails> findAllDraftDetails() {
        return mtgRepo.findAllDraftDetails();
    }

    public List<DeckDetails> findDeckDetailsListByDraftId(String draftId) {
        return mtgRepo.findDeckDetailsListByDraftId(draftId);
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
