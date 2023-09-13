package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.models.DeckDetails;
import sg.edu.nus.iss.mtg_server.models.DraftDetails;
import sg.edu.nus.iss.mtg_server.models.User;

@Repository
@AllArgsConstructor
public class MTGRepository {

    private static final String SQL_FIND_USER = """
            select *
            from users
            where user_name = ?;
            """;

    private static final String SQL_INSERT_USER = """
            insert into users
            values (?, ?, ?, ?);
            """;

    private static final String SQL_INSERT_DRAFT_DETAILS = """
            insert into draft_details
            values (?, ?, ?, ?, ?);
            """;

    private static final String SQL_INSERT_DECK_DETAILS = """
            insert into deck_details
            values (?, ?, ?);
            """;

    // private static final String SQL_FIND_DECK_DETAILS_BY_USER_ID = """
    //         select *
    //         from deck_details
    //         where user_id = ?;
    //         """;

    private static final String SQL_FIND_DECK_DETAILS_BY_DRAFT_ID = """
            select *
            from deck_details
            where draft_id = ?;
            """;

    // private static final String SQL_FIND_DRAFT_DETAILS_LIST_BY_USER_ID = """
    //         select *
    //         from draft_details
    //         where user_id = ?;
    //         """;

    private static final String SQL_FIND_ALL_DRAFT_DETAILS = """
            select *
            from draft_details
            """;

    private static final String SQL_FIND_DRAFT_DETAILS_BY_DRAFT_ID = """
            select *
            from draft_details
            where draft_id = ?;
            """;

    private final JdbcTemplate template;

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

    public boolean insertDraftDetails(DraftDetails draftDetails) {
        return template.update(
                SQL_INSERT_DRAFT_DETAILS,
                draftDetails.getDraftId(),
                draftDetails.getDraftSet(),
                draftDetails.getDraftDate(),
                draftDetails.getNumberOfPlayers(),
                draftDetails.getDecksCreated()) > 0;
    }

    public boolean insertDeckDetails(DeckDetails deckDetails) {
        return template.update(
                SQL_INSERT_DECK_DETAILS,
                deckDetails.getDeckId(),
                deckDetails.getDeckName(),
                deckDetails.getDraftId()) > 0;
    }

    // public List<DeckDetails> findDeckDetailsListByUserId(String userId) {
    //     return template.query(
    //             SQL_FIND_DECK_DETAILS_BY_USER_ID,
    //             BeanPropertyRowMapper.newInstance(DeckDetails.class),
    //             userId);
    // }

    public List<DeckDetails> findDeckDetailsListByDraftId(String draftId) {
        return template.query(
                SQL_FIND_DECK_DETAILS_BY_DRAFT_ID,
                BeanPropertyRowMapper.newInstance(DeckDetails.class),
                draftId);
    }

//     public List<DraftDetails> findDraftDetailsListByUserId(String userId) {
//         return template.query(
//                 SQL_FIND_DRAFT_DETAILS_LIST_BY_USER_ID,
//                 BeanPropertyRowMapper.newInstance(DraftDetails.class),
//                 userId);
//     }

    public List<DraftDetails> findAllDraftDetails() {
        return template.query(
                SQL_FIND_ALL_DRAFT_DETAILS,
                BeanPropertyRowMapper.newInstance(DraftDetails.class));
    }

    public DraftDetails findDraftDetailsByDraftId(String draftId) {
        List<DraftDetails> draftDetailsList = template.query(
                SQL_FIND_DRAFT_DETAILS_BY_DRAFT_ID,
                BeanPropertyRowMapper.newInstance(DraftDetails.class),
                draftId);

        if (draftDetailsList.isEmpty())
            return null;

        return draftDetailsList.get(0);
    }
}
