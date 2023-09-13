package sg.edu.nus.iss.mtg_server.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String playerName;
    private String playerId;

    public JsonObjectBuilder toJsonObjectBuilder() {
        return Json.createObjectBuilder()
                .add("playerName", this.playerName)
                .add("playerId", this.playerId);
    }

    public static Player fromJson(String jsonString) {
        JsonObject obj = Json.createReader(new StringReader(jsonString))
                .readObject();

        return new Player(
                obj.getString("playerName"),
                obj.getString("playerId"));
    }
}
