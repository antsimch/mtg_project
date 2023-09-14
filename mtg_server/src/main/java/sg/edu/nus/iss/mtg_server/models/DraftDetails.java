package sg.edu.nus.iss.mtg_server.models;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftDetails {
    private String draftId;
    private String draftSet;
    private String userId;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("draftId", this.draftId)
                .add("draftSet", this.draftSet)
                .add("userId", this.userId);
    }
}
