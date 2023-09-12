package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.Draft;
import sg.edu.nus.iss.mtg_server.models.User;

@Repository
public class DraftRepository {

    private static final String SQL_FIND_USER = """
            select *
            from users
            where user_name = ?;
            """;

    private static final String SQL_INSERT_USER = """
            insert into users
            values (?, ?, ?, ?);
            """;

    private static final String SQL_INSERT_DRAFT = """
            insert into drafts
            values (?, ?, ?, ?);
            """;

    private static final String SQL_INSERT_DECK_DETAILS = """
            insert into deck_details
            values (?, ?, ?, ?);
            """;

    private static final String SQL_FIND_DECK_DETAILS_BY_USER_ID = """
            select *
            from deck_details
            where user_id = ?;
            """;

    private static final String SQL_FIND_DECK_DETAILS_BY_DRAFT_ID = """
            select *
            from deck_details
            where draft_id = ?;
            """;

    private static final String SQL_FIND_DRAFTS_BY_USER_ID = """
            select *
            from drafts
            where user_id = ?;
            """;

    private static final String SQL_FIND_DRAFT_BY_DRAFT_ID = """
            select *
            from drafts
            where draft_id = ?;
            """;

    private JdbcTemplate template;

    public DraftRepository(JdbcTemplate template) {
        this.template = template;
    }

    public User findUser(String username) {
        List<User> users = template.query(SQL_FIND_USER,
                BeanPropertyRowMapper.newInstance(User.class),
                username);

        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    public boolean insertUser(User user) {
        return template.update(
                SQL_INSERT_USER,
                user.getUserId(),
                user.getUsername(),
                user.getUserEmail(),
                user.getUserPassword()) > 0;
    }

    public boolean insertDraft(Draft draft) {
        return template.update(
                SQL_INSERT_DRAFT,
                draft.getDraftId(),
                draft.getDraftSet(),
                draft.getDraftDate(),
                draft.getNumberOfPlayers()) > 0;
    }

    public boolean insertDeckDetails(DeckDetails deckDetails) {
        return template.update(
                SQL_INSERT_DECK_DETAILS,
                deckDetails.getDeckId(),
                deckDetails.getDeckName(),
                deckDetails.getUserId(),
                deckDetails.getDraftId()) > 0;
    }

    public List<DeckDetails> findDecksByUserId(String deckId) {
        return template.query(
                SQL_FIND_DECK_DETAILS_BY_USER_ID,
                BeanPropertyRowMapper.newInstance(DeckDetails.class),
                deckId);
    }

    public List<DeckDetails> findDecksByDraftId(String draftId) {
        return template.query(
                SQL_FIND_DECK_DETAILS_BY_DRAFT_ID,
                BeanPropertyRowMapper.newInstance(DeckDetails.class),
                draftId);
    }

    public List<Draft> findDraftsByUserId(String userId) {
        return template.query(
                SQL_FIND_DRAFTS_BY_USER_ID,
                BeanPropertyRowMapper.newInstance(Draft.class),
                userId);
    }

    public Draft findDraftByDraftId(String draftId) {
        List<Draft> drafts = template.query(
                SQL_FIND_DRAFT_BY_DRAFT_ID,
                BeanPropertyRowMapper.newInstance(Draft.class),
                draftId);

        if (drafts.isEmpty())
            return null;

        return drafts.get(0);
    }
}
