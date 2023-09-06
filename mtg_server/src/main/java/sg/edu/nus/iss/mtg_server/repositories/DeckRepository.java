package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DeckRepository {

    private static final String C_NAME = "decks";

    private MongoTemplate template;

    public DeckRepository(MongoTemplate template) {
        this.template = template;
    }

    public String insertDeck(Document document) {
        Document newDoc = template.insert(document, C_NAME);
        return newDoc == null? null : newDoc.get("_id").toString();
    }

    public Document findDeckByDeckId(String deckId) {
        Query query = Query.query(Criteria.where("deck_id").is(deckId));
        List<Document> docs = template.find(query, Document.class);
        return docs.isEmpty()? null: docs.get(0);
    }

    public List<Document> findDecksByUserId(String userId) {
        Query query = Query.query(Criteria.where("user_id").is(userId));
        List<Document> docs = template.find(query, Document.class);
        return docs.isEmpty()? null: docs;
    }

    public List<Document> findDecksByDraftId(String draftId) {
        Query query = Query.query(Criteria.where("user_id").is(draftId));
        List<Document> docs = template.find(query, Document.class);
        return docs.isEmpty()? null: docs;
    }
}
