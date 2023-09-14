package sg.edu.nus.iss.mtg_server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import sg.edu.nus.iss.mtg_server.exceptions.UpdateException;
import sg.edu.nus.iss.mtg_server.models.Card;
import sg.edu.nus.iss.mtg_server.models.Deck;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.services.MTGIOApiService;
import sg.edu.nus.iss.mtg_server.services.MTGService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class MTGController {

    private final MTGService mtgSvc;
    private final MTGIOApiService mtgioApiSvc;

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

        System.out.println("\n\nIn saveDeck endpoint \n\n");
        try {
            mtgSvc.insertDeck(deck);

            objbuilder.add(
                    "message",
                    String.format(
                            "Your deck %s(id: %s) has been saved succesfully",
                            deck.getDeckName(),
                            deck.getDeckId()));

            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (UpdateException ex) {

            objbuilder.add("message", ex.getMessage());

            return ResponseEntity.internalServerError().body(
                    objbuilder.build().toString());
        }
    }

    /*
     * Endpoint for retrieving a deck by deckId
     * Controller returns { "deckId": , "deckName": , "userId": , "cards": []} 
     * upon successfully retrieving the deck from the database or
     * 404 if the deck is not found.
     */
    @GetMapping(path = "/deck/{deckId}")
    public ResponseEntity<String> getDeckByDeckId(@PathVariable String deckId) {

        try {
            Deck deck = mtgSvc.findDeckByDeckId(deckId);

            System.out.println("\n\n" + deck + "\n\n");

            return ResponseEntity.ok(
                    deck.toJsonObjectBuilder().build().toString());

        } catch (DeckNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Endpoint for deleting a deck
     */
    @DeleteMapping(path = "/deck/{deckId}")
    public ResponseEntity<String> deleteDeckByDeckId(@PathVariable String deckId) {
        
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        try {
            mtgSvc.deleteDeckByDeckId(deckId);
            
            objBuilder.add("message", 
                    String.format("Deck (id: %s) has been deleted successfully", deckId));

            return ResponseEntity.ok(objBuilder.build().toString());

        } catch (UpdateException ex) {
            objBuilder.add("message", ex.getMessage());
            return ResponseEntity.internalServerError()
                    .body(objBuilder.build().toString());
        }
    }

    /*
     * Endpoint for getting a card from database
     */
    @GetMapping(path = "/card/{cardId}")
    public ResponseEntity<String> getCardById(@PathVariable String cardId) {

        try {
            Card card = mtgSvc.findCardByCardId(cardId);
            return ResponseEntity.ok(card.toJsonObjectBuilder().build().toString());
            
        } catch (CardNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Endpoint for retrieving list of deckDetails by userId.
     * Controller returns [...deckDetails] upon successfully retrieving the list of 
     * decks or 404 if no decks are found.
     */
    @GetMapping(path = "/decks/{userId}")
    public ResponseEntity<String> getDecksByUserId(
            @PathVariable String userId) {

        List<DeckDetails> deckDetailsList = 
                mtgSvc.findDeckDetailsListByUserId(userId);

        if (deckDetailsList.isEmpty())
            return ResponseEntity.notFound().build();

        JsonArrayBuilder deckDetailsArrBuilder = Json.createArrayBuilder();

        deckDetailsList.stream()
                .map(deckDetails -> deckDetails.toJsonObjectBuilder())
                .forEach(objBuilder -> deckDetailsArrBuilder.add(objBuilder));

        return ResponseEntity.ok(deckDetailsArrBuilder.build().toString());
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
