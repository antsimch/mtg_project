package sg.edu.nus.iss.mtg_server.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Draft {
    private String draftId;
    private Player player;
    private DraftStatus status;
    private String set;
    private List<Card> cards;
    private int numberOfBoosterPacks;

    public Draft() {
        this.cards = new ArrayList<Card>();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.numberOfBoosterPacks = 24;
    }

    public JsonObjectBuilder toJsonObjectBuilder() {

        JsonArrayBuilder cardsArrBuilder = Json.createArrayBuilder();
        this.cards.stream()
                .map(card -> card.toJsonObjectBuilder())
                .forEach(objBuilder -> cardsArrBuilder.add(objBuilder));

        return Json.createObjectBuilder()
                .add("draftId", this.draftId)
                .add("player", Json.createObjectBuilder()
                        .add("playerName", this.player.getPlayerName())
                        .add("playerId", this.player.getPlayerId()))
                .add("status", this.status.name())
                .add("set", this.set)
                .add("cards", cardsArrBuilder);
    }
}
