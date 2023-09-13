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
public class CardRepository {
    
    private static final String C_NAME = "cards";

    private final MongoTemplate template;

    public List<String> findAllSets() {
		return template.findDistinct(new Query(), 
                "set", 
                C_NAME, 
                String.class);
	}

    public Document findCardByCardId(String cardId) {

        Query query = Query.query(Criteria.where("id").is(cardId));
        List<Document> docs = template.find(
                query, Document.class, C_NAME);

        return docs.isEmpty()? null: docs.get(0);
    }
}
