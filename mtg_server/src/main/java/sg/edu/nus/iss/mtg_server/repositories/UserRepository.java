package sg.edu.nus.iss.mtg_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.models.User;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final String SQL_FIND_USER = """
            select *
            from users
            where username = ?;
            """;

    private static final String SQL_INSERT_USER = """
            insert into users
            values (?, ?, ?, ?, ?);
            """;

    private static final String SQL_UPDATE_USER_DECKS = """
            update users set user_decks = user_decks + ?
            where user_id = ?
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
                user.getUserPassword(),
                0) > 0;
    }

    public boolean updateUserDecks(String userId, int change) {
        return template.update(SQL_UPDATE_USER_DECKS, change, userId) > 0;
    }
}
