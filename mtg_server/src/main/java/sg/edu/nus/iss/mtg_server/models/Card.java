package sg.edu.nus.iss.mtg_server.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String cardId;
    private String cardName;
    private Integer cardCMC;
    private List<String> cardColors;
    private String cardType;
    private String cardRarity;
    private String cardSet;
    private String cardImageUrl;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("cardId", this.cardId)
                .add("cardName", this.cardName)
                .add("cardCMC", this.cardCMC)
                .add("cardColors", 
                        Json.createArrayBuilder(this.cardColors))
                .add("cardType", this.cardType)
                .add("cardRarity", this.cardRarity)
                .add("cardSet", this.cardSet)
                .add("cardImageUrl", this.cardImageUrl);
    }

    public static Card fromJson(String jsonString) {
        JsonObject obj = Json.createReader(new StringReader(jsonString))
                .readObject();

        return new Card(
            obj.getString("cardId"),
            obj.getString("cardName"),
            obj.getJsonNumber("cardCMC").intValue(),
            obj.getJsonArray("cardColors").stream()
                    .map(v -> v.toString())
                    .toList(),
            obj.getString("cardType"),
            obj.getString("cardRarity"),
            obj.getString("cardSet"),
            obj.getString("cardImageUrl")
        );
    }
}
