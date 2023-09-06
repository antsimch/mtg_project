package sg.edu.nus.iss.mtg_server.controllers;

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
import sg.edu.nus.iss.mtg_server.exceptions.DeckNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.FailedToSaveDeckException;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidUsernameOrPasswordException;
import sg.edu.nus.iss.mtg_server.exceptions.UsernameAlreadyTakenException;
import sg.edu.nus.iss.mtg_server.models.Deck;
import sg.edu.nus.iss.mtg_server.models.LoginDetails;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.services.MTGService;

@RestController
@RequestMapping(path = "/api")
public class MTGController {

    private MTGService mtgSvc;

    public MTGController(MTGService mtgSvc) {
        this.mtgSvc = mtgSvc;
    }

    
    /*
     * Endpoint for creation of new user
     */
    @PostMapping(path = "/new")
    public ResponseEntity<String> createUser(
            @RequestBody @Valid User loginDetails,
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
            mtgSvc.insertUser(loginDetails);
            objbuilder.add("message", "User has been created.");
            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (UsernameAlreadyTakenException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }


    /*
     * Endpoint for login authentication
     * TODO: include JWT
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
            mtgSvc.authenticate(
                    loginDetails.getUserName(),
                    loginDetails.getUserPassword());

            objbuilder.add(
                    "message",
                    String.format("Welcome, %s\n", loginDetails.getUserName()));

            return ResponseEntity.ok(objbuilder.build().toString());
        } catch (InvalidUsernameOrPasswordException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }


    /*
     * Endpoint for saving a deck
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
     * Endpoint for retrieving a deck by deckId
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
     * Endpoint for retrieving list of decks by userId
     */
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<String> getDecksByUserId(
            @PathVariable String userId) {

        List<Deck> decks = mtgSvc.findDecksByUserId(userId);

        if (decks.isEmpty())
            return ResponseEntity.notFound().build();

        JsonArrayBuilder deckArrBuilder = Json.createArrayBuilder();

        decks.stream()
                .map(deck -> deck.toJsonObjectBuilder())
                .forEach(objBuilder -> deckArrBuilder.add(objBuilder));

        return ResponseEntity.ok(deckArrBuilder.build().toString());
    }


    /*
     * Endpoint for retrieving list of decks by draftId
     */
    @GetMapping(path = "/draft/{draftId}") 
    public ResponseEntity<String> getDecksByDraftId(
            @PathVariable String draftId) {
        
        List<Deck> decks = mtgSvc.findDecksByDraftId(draftId);

        if (decks.isEmpty())
            return ResponseEntity.notFound().build();

        JsonArrayBuilder deckArrBuilder = Json.createArrayBuilder();

        decks.stream()
                .map(deck -> deck.toJsonObjectBuilder())
                .forEach(objBuilder -> deckArrBuilder.add(objBuilder));

        return ResponseEntity.ok(deckArrBuilder.build().toString());
    }
}
