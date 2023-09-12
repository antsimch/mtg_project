package sg.edu.nus.iss.mtg_server.services;

import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.mtg_server.exceptions.DeckNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.FailedToSaveDeckException;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidUsernameOrPasswordException;
import sg.edu.nus.iss.mtg_server.exceptions.UsernameAlreadyTakenException;
import sg.edu.nus.iss.mtg_server.models.Deck;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.Draft;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.models.Util;
import sg.edu.nus.iss.mtg_server.repositories.DeckRepository;
import sg.edu.nus.iss.mtg_server.repositories.DraftRepository;

@Service
public class MTGService {

    private DraftRepository draftRepo;
    private DeckRepository deckRepo;

    public MTGService(DraftRepository draftRepo, DeckRepository deckRepo) {
        this.draftRepo = draftRepo;
        this.deckRepo = deckRepo;
    }

    /*
     * Service for creation of new user, checks for duplicate username in
     * database and throws custom UsernameAlreadyTakenException if database
     * already contains the same username
     */
    public void insertUser(User user) throws UsernameAlreadyTakenException {
        User result = draftRepo.findUser(user.getUsername());

        if (result != null)
            throw new UsernameAlreadyTakenException(
                    "Username is already taken");

        user.setUserId(UUID.randomUUID().toString().substring(0, 16));
        System.out.println("\n\nuserId >>>> " + user.getUserId() + "\n\n");
        draftRepo.insertUser(user);
    }

    /*
     * Authentication of user for login
     * TODO: Hashing and JWT implementation
     */
    public String authenticate(
            String username,
            String password) throws InvalidUsernameOrPasswordException {
        User result = draftRepo.findUser(username);

        if (result == null) {
            throw new InvalidUsernameOrPasswordException(
                    "Invalid username and/or password");
        }

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

    public List<DeckDetails> findDecksByUserId(String userId) {
        return draftRepo.findDecksByUserId(userId);
    }

    public List<Draft> findDraftsByUserId(String userId) {
        return draftRepo.findDraftsByUserId(userId);
    }

    public List<DeckDetails> findDecksByDraftId(String draftId) {
        return draftRepo.findDecksByDraftId(draftId);
    }
}
