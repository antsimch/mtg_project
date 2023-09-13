package sg.edu.nus.iss.mtg_server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.exceptions.CardNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.DraftIsAlreadyCompletedException;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidDraftIdException;
import sg.edu.nus.iss.mtg_server.models.Card;
import sg.edu.nus.iss.mtg_server.models.Draft;
import sg.edu.nus.iss.mtg_server.models.DraftStatus;
import sg.edu.nus.iss.mtg_server.models.CardPick;
import sg.edu.nus.iss.mtg_server.models.Player;
import sg.edu.nus.iss.mtg_server.storage.DraftStorage;

@Service
@AllArgsConstructor
public class DraftService {

    private final MTGIOApiService mtgioApiSvc;
    private final MTGService mtgSvc;

    public Draft createDraft(Player player, String set) 
            throws CardNotFoundException {
                
        Draft draft = new Draft();
        draft.setDraftId(UUID.randomUUID().toString().substring(0, 8));
        draft.setPlayer(player);
        draft.setStatus(DraftStatus.NEW);
        draft.setSet(set);
        draft.setCards(newBoosterPack(set));
        draft.setNumberOfBoosterPacks(draft.getNumberOfBoosterPacks() - 1);
        DraftStorage.getInstance().setDraft(draft);
        return draft;
    }

    public Draft pickCard(CardPick pick) 
            throws InvalidDraftIdException, 
            CardNotFoundException, 
            DraftIsAlreadyCompletedException {

        if (!DraftStorage.getInstance().getDrafts().containsKey(pick.getDraftId()))
            throw new InvalidDraftIdException(
                    "Draft with provided id doesn't exist");

        Draft draft = DraftStorage.getInstance().getDrafts().get(pick.getDraftId());

        if (draft.getStatus().name().equals(DraftStatus.COMPLETED.name()))
            throw new DraftIsAlreadyCompletedException(
                    "Draft is completed");

        draft.getCards().remove(pick.getIndex());

        for (int i = 0; i < 7; i++) {

            if (draft.getCards().size() != 0) {
                int indexToRemove = Math.abs(new Random().nextInt()) % draft.getCards().size();
                System.out.println("\n\nindexToRemove >>>> " + indexToRemove + "\n\n");
                draft.getCards().remove(indexToRemove);
            } 

            if (draft.getCards().size() == 0 && draft.getNumberOfBoosterPacks() != 0) {
                draft.setCards(newBoosterPack(draft.getSet()));
                draft.setNumberOfBoosterPacks(draft.getNumberOfBoosterPacks() - 1);
                int indexToRemove = Math.abs(new Random().nextInt()) % draft.getCards().size();
                System.out.println("\n\nindexToRemove >>>> " + indexToRemove + "\n\n");
                draft.getCards().remove(indexToRemove);
            }
        }

        if (draft.getCards().size() == 0) {

            if (draft.getNumberOfBoosterPacks() == 0) {

                draft.setStatus(DraftStatus.COMPLETED);
                DraftStorage.getInstance().setDraft(draft);
                return draft;

            } else {

                draft.setCards(newBoosterPack(draft.getSet()));
                draft.setNumberOfBoosterPacks(draft.getNumberOfBoosterPacks() - 1);
            }
        }

        DraftStorage.getInstance().setDraft(draft);
        return draft;
    }

    private List<Card> newBoosterPack(String set) throws CardNotFoundException {
        
        List<String> boosterPack = mtgioApiSvc.getBoosterPackFromAPI(set);
        List<Card> cards = new ArrayList<>();

        for (String cardId : boosterPack) {
            cards.add(mtgSvc.findCardByCardId(cardId));
        }

        return cards;
    }
}
