package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.data.LoginDataRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoginDataRepositoryJdbcImpl implements LoginDataRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder encoder;

    public LoginDataRepositoryJdbcImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate,
            PasswordEncoder encoder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.encoder = encoder;
    }

    @Override
    public boolean userExists(String login) {
        String query = "SELECT COUNT(user_login) FROM users "
                + "WHERE user_login = :user_login";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        @SuppressWarnings("ConstantConditions")
        int count =
                namedParameterJdbcTemplate.queryForObject(query, params, Integer.class);
        return count != 0;
    }

    @Override
    public void insertUser(String login, String password) {
        String update = "INSERT INTO users "
                + "(user_login, user_password) VALUES (:user_login, :user_password)";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        params.put("user_password", encoder.encode(password));
        namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public void insertRole(String login, String role) {
        jdbcTemplate.update(
                "INSERT INTO roles (user_login, user_role) VALUES (?, ?)",
                login, role);
    }

    @Override
    public void removeUser(String login) {
        jdbcTemplate.update("DELETE FROM users WHERE user_login = ?", login);
    }

    @Override
    public void removeRole(String login, String role) {
        String update = "DELETE FROM roles "
                + "WHERE user_login = :user_login AND user_role = :user_role";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        params.put("user_role", role);
        namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public void removeRoles(String login) {
        String update = "DELETE FROM roles WHERE user_login = :user_login";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public List<String> getAllLogins(String role) {
        return jdbcTemplate.queryForList(
                "SELECT user_login FROM roles WHERE user_role = ?",
                String.class, role);
    }

    @Override
    public List<String> getAllRoles(String login) {
        String query = "SELECT user_role FROM roles WHERE user_login = :user_login";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        return namedParameterJdbcTemplate.queryForList(query, params, String.class);
    }

    @Override
    public void updatePassword(String login, String password) {
        String update = "UPDATE users SET "
                + "user_password = :user_password WHERE user_login = :user_login";
        Map<String, Object> params = new HashMap<>();
        params.put("user_login", login);
        params.put("user_password", encoder.encode(password));
        namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public boolean checkPassword(String login, String password) {
        String stored_password = getPassword(login);
        return encoder.matches(password, stored_password);
    }

    @Override
    public String getPassword(String login) {
        return jdbcTemplate.queryForObject(
                "SELECT user_password FROM users WHERE user_login = ?",
                String.class, login);
    }

}
