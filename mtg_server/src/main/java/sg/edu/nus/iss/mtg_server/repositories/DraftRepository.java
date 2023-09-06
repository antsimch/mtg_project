package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.mtg_server.models.Deck;
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

    private static final String SQL_FIND_DECKS_BY_USER_ID = """
            select *
            from decks
            where user_id = ?;
            """;
    
    private static final String SQL_FIND_DECKS_BY_DRAFT_ID = """
            select *
            from decks
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
                user.getUserName(),
                user.getUserPassword(),
                user.getEmail()) > 0;
    }

    public List<Deck> findDecksByUserId(String deckId) {
        return template.query(
                SQL_FIND_DECKS_BY_USER_ID,
                BeanPropertyRowMapper.newInstance(Deck.class), 
                deckId);
    }

    public List<Deck> findDecksByDraftId(String draftId) {
        return template.query(
                SQL_FIND_DECKS_BY_DRAFT_ID,
                BeanPropertyRowMapper.newInstance(Deck.class), 
                draftId);
    }
}
