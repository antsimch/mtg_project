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

    // public static Draft fromJson(String jsonString) {

    //     JsonObject obj = Json.createReader(new StringReader(jsonString))
    //             .readObject();

    //     JsonArray playerArr = obj.getJsonArray("players");
    //     List<Player> players = new ArrayList<>();
    //     playerArr.stream()
    //             .map(value -> value.asJsonObject().toString())
    //             .map(jsonStr -> Player.fromJson(jsonStr))
    //             .forEach(player -> players.add(player));

    //     JsonArray cardArr = obj.getJsonArray("cards");
    //     List<Card> cards = new ArrayList<>();
    //     cardArr.stream()
    //             .map(value -> value.asJsonObject().toString())
    //             .map(jsonStr -> Card.fromJson(jsonStr))
    //             .forEach(card -> cards.add(card));

    //     System.out.println("\n\n" + players.toString() + "\n\n");
    //     System.out.println("\n\n" + cards.toString() + "\n\n");

    //     return new Draft(
    //         obj.getString("draftId"),
    //         players,
    //         DraftStatus.valueOf(obj.getString("status")),
    //         obj.getString("set"),
    //         cards,
    //         obj.getJsonNumber("numberOfBoosterPacks").intValue(),
    //         obj.getString("playerTurn")
    //     );
    // }
}
