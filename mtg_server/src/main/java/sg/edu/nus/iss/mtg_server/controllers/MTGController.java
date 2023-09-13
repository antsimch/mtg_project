package sg.edu.nus.iss.mtg_server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
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
import sg.edu.nus.iss.mtg_server.models.LoginDetails;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.services.CardPoolService;
import sg.edu.nus.iss.mtg_server.services.MTGIOApiService;
import sg.edu.nus.iss.mtg_server.services.MTGService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class MTGController {

    private final MTGService mtgSvc;
    private final CardPoolService cardPoolSvc;
    private final MTGIOApiService mtgioApiSvc;

    /*
     * Endpoint for creation of new user, includes validation of User object.
     * Controller returns {"message": "User has been created"} upon successfully 
     * inserting the user into database or {"message": "Username is already 
     * taken"} if database already contains the same username
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> createUser(
            @RequestBody @Valid User user,
            BindingResult binding) {

        JsonObjectBuilder objbuilder = Json.createObjectBuilder();

        if (binding.hasErrors()) {
            binding.getAllErrors()
                    .forEach(error -> {
                        objbuilder.add(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage());
                    });

            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }

        try {
            mtgSvc.insertUser(user);
            objbuilder.add("message", "User has been created.");
            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (UsernameAlreadyTakenException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }

    /*
     * Endpoint for login authentication includes validation of LoginDetails
     * object. Controller returns {"message": "Welcome {username}", 
     * "userId": {userId}} upon successful login or {"message": "Invalid 
     * username and/or password"} if login is unsuccessful.
     * 
     * TODO: Hashing and JWT implementation
     */
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid LoginDetails loginDetails,
            BindingResult binding) {

        JsonObjectBuilder objbuilder = Json.createObjectBuilder();

        if (binding.hasErrors()) {

            binding.getAllErrors()
                    .forEach(error -> {
                        objbuilder.add(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage());
                    });

            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }

        try {
            String userId = mtgSvc.authenticate(
                    loginDetails.getUsername(),
                    loginDetails.getPassword());

            objbuilder.add(
                    "message",
                    String.format("Welcome, %s\n", loginDetails.getUsername()))
                    .add("userId", userId);

            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (InvalidUsernameOrPasswordException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }

    /*
     * Endpoint for saving a deck, includes validation of Deck object.
     * Controller returns {"message": "Your deck {deckName}(id: {deckId}) has 
     * been successfully saved"} upon successfully inserting the deck into 
     * database or {"message": "An error has occured when saving your deck"}
     * if insertion is unsuccessful.
     */
    @PostMapping(path = "/save")
    public ResponseEntity<String> saveDeck(
            @RequestBody @Valid Deck deck,
            BindingResult binding) {

        JsonObjectBuilder objbuilder = Json.createObjectBuilder();

        if (binding.hasErrors()) {

            binding.getAllErrors()
                    .forEach(error -> {
                        objbuilder.add(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage());
                    });

            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }

        try {
            mtgSvc.insertDeck(deck);

            objbuilder.add(
                    "message",
                    String.format(
                            "Your deck %s(id: %s) has been succesfully saved",
                            deck.getDeckName(),
                            deck.getDeckId()));

            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (FailedToSaveDeckException ex) {

            objbuilder.add("message", ex.getMessage());

            return ResponseEntity.internalServerError().body(
                    objbuilder.build().toString());
        }
    }

    /*
     * Endpoint for retrieving list of decks by userId.
     * Controller returns [...decks] upon successfully retrieving the list of 
     * decks or 404 if no decks are found.
     */
    // @GetMapping(path = "/decks/{userId}")
    // public ResponseEntity<String> getDecksByUserId(
    //         @PathVariable String userId) {

    //     List<DeckDetails> deckDetailsList = mtgSvc.findDecksByUserId(userId);

    //     if (deckDetailsList.isEmpty())
    //         return ResponseEntity.notFound().build();

    //     JsonArrayBuilder deckDetailsArrBuilder = Json.createArrayBuilder();

    //     deckDetailsList.stream()
    //             .map(deckDetails -> deckDetails.toJsonObjectBuilder())
    //             .forEach(objBuilder -> deckDetailsArrBuilder.add(objBuilder));

    //     return ResponseEntity.ok(deckDetailsArrBuilder.build().toString());
    // }

    /*
     * Endpoint for retrieving a deck by deckId
     * Controller returns { "deckId": , "deckName": , "userId": , "draftId:" , 
     * "cards": []} upon successfully retrieving the deck from the database or
     * 404 if the deck is not found.
     */
    @GetMapping(path = "/deck/{deckId}")
    public ResponseEntity<String> getDeckByDeckId(@PathVariable String deckId) {

        try {
            Deck deck = mtgSvc.findDeckByDeckId(deckId);

            return ResponseEntity.ok(
                    deck.toJsonObjectBuilder().build().toString());

        } catch (DeckNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Endpoint for getting list of draftDetailsList by userId
     */
    // @GetMapping(path = "/draftDetailsList/{userId}")
    // public ResponseEntity<String> getDraftsByUserId(
    //         @PathVariable String userId) {
        
    //     List<Draft> draftDetailsList = mtgSvc.findDraftsByUserId(userId);

    //     if (draftDetailsList.isEmpty())
    //         return ResponseEntity.notFound().build();

    //     JsonArrayBuilder draftArrBuilder = Json.createArrayBuilder();
    //     draftDetailsList.stream()
    //             .map(draft -> draft.toJsonObjectBuilder())
    //             .forEach(objBuilder -> draftArrBuilder.add(objBuilder));

    //     return ResponseEntity.ok(draftArrBuilder.build().toString());
    // }

    /*
     * Endpoint for getting list of draftDetailsList
     */
    @GetMapping(path = "/draftDetailsList")
    public ResponseEntity<String> getDraftsByUserId() {
        
        List<DraftDetails> draftDetailsList = mtgSvc.findAllDraftDetails();

        if (draftDetailsList.isEmpty())
            return ResponseEntity.notFound().build();

        JsonArrayBuilder draftArrBuilder = Json.createArrayBuilder();
        draftDetailsList.stream()
                .map(draft -> draft.toJsonObjectBuilder())
                .forEach(objBuilder -> draftArrBuilder.add(objBuilder));

        return ResponseEntity.ok(draftArrBuilder.build().toString());
    }


    /*
     * Endpoint for retrieving list of decks by draftId
     */
    @GetMapping(path = "/decks/{draftId}")
    public ResponseEntity<String> getDecksByDraftId(
            @PathVariable String draftId) {

        List<DeckDetails> deckDetailsList = 
                mtgSvc.findDeckDetailsListByDraftId(draftId);

        if (deckDetailsList.isEmpty())
            return ResponseEntity.notFound().build();

        JsonArrayBuilder deckDetailsArrBuilder = Json.createArrayBuilder();

        deckDetailsList.stream()
                .map(deckDetails -> deckDetails.toJsonObjectBuilder())
                .forEach(objBuilder -> deckDetailsArrBuilder.add(objBuilder));

        return ResponseEntity.ok(deckDetailsArrBuilder.build().toString());
    }

    /*
     * Endpoint for retrieving drafted cards from database
     */
    @GetMapping(path = "/pool/{userId}")
    public ResponseEntity<String> getCardPoolByUserId(
            @PathVariable String draftId) {
        
        List<Card> cards = cardPoolSvc.findCardPoolByDraftId(draftId);

        if (cards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        cards.stream()
                .map(card -> card.toJsonObjectBuilder())
                .forEach(jsonObj -> arrBuilder.add(jsonObj));

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    /*
     * Endpoint for saving drafted cards to database
     */
    @PostMapping(path = "/pool/{userId}")
    public ResponseEntity<String> saveCardPool(
            @RequestBody List<Card> cards, 
            @PathVariable String userId) {

        boolean insertSuccessful = cardPoolSvc.saveCardPool(cards, userId);
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        if (!insertSuccessful){
            objBuilder.add("message", "Error with caching card pool");
            return ResponseEntity.internalServerError().body(
                objBuilder.build().toString());
        }

        objBuilder.add("message", "Card pool cached successfully");

        return ResponseEntity.ok().body(objBuilder.build().toString());
    }

    /*
     * Endpoint for retrieving list of sets available in database
     */
    @GetMapping(path = "/sets")
    public ResponseEntity<String> getAllSets() {

        List<String> sets = mtgSvc.findAllSets();

        if (sets.isEmpty()) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("message", "Unable to connect to database");

            return ResponseEntity.internalServerError().body(
                    objBuilder.build().toString());
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        sets.stream().forEach(v -> arrBuilder.add(v));

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    /*
     * Endpoint for generating a booster pack
     */
    @GetMapping(path = "/pack/{set}")
    public ResponseEntity<String> getBoosterPack(@PathVariable String set) {

        List<String> boosterPack = mtgioApiSvc.getBoosterPackFromAPI(set);
        List<Card> cards = new ArrayList<>();

        try {
            for (String cardId : boosterPack) {
                cards.add(mtgSvc.findCardByCardId(cardId));
            }

            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            cards.stream()
                    .map(card -> card.toJsonObjectBuilder())
                    .forEach(objBuilder -> arrBuilder.add(objBuilder));
            
            return ResponseEntity.ok(arrBuilder.build().toString());

        } catch (CardNotFoundException ex) {
            
            return ResponseEntity.notFound().build();
        }
    }
}
