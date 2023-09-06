package sg.edu.nus.iss.mtg_server.models;

import org.bson.Document;

import jakarta.json.Json;

public class Util {
    
    public static Document parseDeckToDocument(Deck deck) {
        
        Document doc = new Document();
        doc.put("deck_id", deck.getDeckId());
        doc.put("deck_name", deck.getDeckName());
        doc.put("user_id", deck.getUserId());
        doc.put("draft_id", deck.getDraftId());
        doc.put("cards", Json.createArrayBuilder(deck.getCards()).build());

        return doc;
    }

    public static Deck parseDocumentToDeck(Document document) {
        return new Deck(
            document.getString("deck_id"), 
            document.getString("deck_name"), 
            document.getString("user_id"), 
            document.getString("draft_id"), 
            document.getList("cards", String.class));
    }
    
}
