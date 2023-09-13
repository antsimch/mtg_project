package sg.edu.nus.iss.mtg_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.exceptions.CardNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.DraftIsAlreadyCompletedException;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidDraftIdException;
import sg.edu.nus.iss.mtg_server.models.Draft;
import sg.edu.nus.iss.mtg_server.models.CardPick;
import sg.edu.nus.iss.mtg_server.models.Player;
import sg.edu.nus.iss.mtg_server.services.DraftService;

@RestController
@AllArgsConstructor
@RequestMapping("/drafting")
public class DraftController {

    private final DraftService draftSvc;

    @PostMapping("/create/{set}")
    public ResponseEntity<String> create(
            @RequestBody Player player, 
            @PathVariable String set) throws CardNotFoundException {

        Draft draft = draftSvc.createDraft(player, set);

        return ResponseEntity.ok(
                draft.toJsonObjectBuilder().build().toString());
    }

    @PostMapping("/pick")
    public ResponseEntity<String> pickCard(@RequestBody CardPick request) 
                throws InvalidDraftIdException, 
                CardNotFoundException, 
                DraftIsAlreadyCompletedException {

        Draft draft = draftSvc.pickCard(request);

        return ResponseEntity.ok(
                draft.toJsonObjectBuilder().build().toString());
    }
}
