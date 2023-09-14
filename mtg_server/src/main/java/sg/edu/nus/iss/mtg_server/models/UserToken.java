package sg.edu.nus.iss.mtg_server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserToken {
    private String id;
    private String username;
    private String token;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("username", this.username)
                .add("token", this.token)
                .build();
    }
}
