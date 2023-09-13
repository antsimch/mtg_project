package sg.edu.nus.iss.mtg_server.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deck {
    
    private String deckId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String deckName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String userId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String draftId;

    @NotNull
    @NotEmpty
    @NotBlank
    private List<String> cards;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("deckId", this.deckId)
                .add("deckName", this.deckName)
                .add("userId", this.userId)
                .add("draftId", this.draftId)
                .add("cards", 
                        Json.createArrayBuilder(this.cards));
    }
}
