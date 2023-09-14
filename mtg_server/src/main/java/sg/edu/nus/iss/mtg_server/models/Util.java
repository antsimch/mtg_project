package sg.edu.nus.iss.mtg_server.models;

import org.bson.Document;

public class Util {

    public static Deck parseDocumentToDeck(Document document) {
        return new Deck(
                document.getString("deckId"),
                document.getString("deckName"),
                document.getString("userId"),
                document.getList("cards", String.class));
    }

    public static Card parseDocumentToCard(Document document) {
        System.out.println("\n\n" + document + "\n\n");
        return new Card(
                document.getString("id"),
                document.getString("name"),
                document.getDouble("cmc"),
                document.getString("type"),
                document.getString("rarity"),
                document.getString("set"),
                document.getString("imageUrl")
        );
    }
}
