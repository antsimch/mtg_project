package sg.edu.nus.iss.mtg_server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardPick {
    private String draftId;
    private int index;
}
