package sg.edu.nus.iss.mtg_server.models;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Draft {
    private String draftId;
    private String draftSet;
    private String draftDate;
    private Integer numberOfPlayers;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("draftId", this.draftId)
                .add("draftSet", this.draftSet)
                .add("draftDate", this.draftDate)
                .add("numberOfPlayers", this.numberOfPlayers);
    }
}
