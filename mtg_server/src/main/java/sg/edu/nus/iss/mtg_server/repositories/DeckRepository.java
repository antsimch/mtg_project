package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DeckRepository {

    private static final String C_NAME = "decks";

    private final MongoTemplate template;

    public String insertDeck(Document document) {
        Document newDoc = template.insert(document, C_NAME);
        return newDoc == null? null : newDoc.get("_id").toString();
    }

    public Document findDeckByDeckId(String deckId) {
        Query query = Query.query(Criteria.where("deckId").is(deckId));
        List<Document> docs = template.find(
                query, Document.class, C_NAME);

        return docs.isEmpty()? null: docs.get(0);
    }

    public Document deleteDeckByDeckId(String deckId) {
        Query query = Query.query(Criteria.where("deckId").is(deckId));
        Document deleted = template.findAndRemove(query, Document.class, C_NAME);
        return deleted;
    }
}
