package sg.edu.nus.iss.mtg_server.models;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeckDetails {
    private String deckId;
    private String deckName;
    private String draftId;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("deckId", this.deckId)
                .add("deckName", this.deckName)
                .add("draftId", this.draftId);
    }
}
