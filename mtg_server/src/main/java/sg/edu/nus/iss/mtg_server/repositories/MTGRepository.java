package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.DraftDetails;

@Repository
@AllArgsConstructor
public class MTGRepository {

    private static final String SQL_INSERT_DRAFT_DETAILS = """
            insert into draft_details
            values (?, ?, ?);
            """;

    private static final String SQL_INSERT_DECK_DETAILS = """
            insert into deck_details
            values (?, ?);
            """;

    private static final String SQL_FIND_DECK_DETAILS_BY_USER_ID = """
            select *
            from deck_details
            where user_id = ?;
            """;

    private static final String SQL_DELETE_DECK_DETAILS = """
            delete from deck_details
            where deck_id = ?;
            """;

    private final JdbcTemplate template;

    public boolean insertDraftDetails(DraftDetails draftDetails) {
        return template.update(
                SQL_INSERT_DRAFT_DETAILS,
                draftDetails.getDraftId(),
                draftDetails.getDraftSet(),
                draftDetails.getUserId()) > 0;
    }

    public boolean insertDeckDetails(DeckDetails deckDetails) {
        System.out.println("\n\n" + deckDetails + "\n\n");
        return template.update(
                SQL_INSERT_DECK_DETAILS,
                deckDetails.getDeckId(),
                deckDetails.getUserId()) > 0;
    }

    public List<DeckDetails> findDeckDetailsListByUserId(String userId) {
        return template.query(
                SQL_FIND_DECK_DETAILS_BY_USER_ID,
                BeanPropertyRowMapper.newInstance(DeckDetails.class),
                userId);
    }

    public boolean deleteDeckDetailsByDeckId(String deckId) {
        return template.update(
                SQL_DELETE_DECK_DETAILS,
                deckId) > 0;
    }
}
