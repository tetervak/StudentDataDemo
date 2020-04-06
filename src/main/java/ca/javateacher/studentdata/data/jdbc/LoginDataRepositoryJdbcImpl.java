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
    public boolean userExists(String userName) {
        String query = "SELECT COUNT(user_name) FROM user "
                + "WHERE user_name = :user_name";
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", userName);
        @SuppressWarnings("ConstantConditions")
        int count =
                namedParameterJdbcTemplate.queryForObject(query, params, Integer.class);
        return count != 0;
    }

    @Override
    public void insertUser(String userName, String password) {
        String update = "INSERT INTO user "
                + "(user_name, password) VALUES (:user_name, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", userName);
        params.put("password", encoder.encode(password));
        namedParameterJdbcTemplate.update(update, params);
    }

    private Integer findUserIdByUserName(String userName){
        return jdbcTemplate.queryForObject(
                "SELECT id FROM user WHERE user_name = ? LIMIT 1",
                Integer.class,
                userName);
    }

    private Integer findRoleIdByRoleName(String roleName){
        return jdbcTemplate.queryForObject(
                "SELECT id FROM role WHERE role_name = ? LIMIT 1",
                Integer.class,
                roleName);
    }

    @Override
    public void insertRole(String userName, String roleName) {
        Integer userId = findUserIdByUserName(userName);
        if(userId != null){
            Integer roleId = findRoleIdByRoleName(roleName);
            if(roleId != null){
                jdbcTemplate.update(
                        "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)",
                        userId, roleId);
            }
        }
    }



    @Override
    public void removeUser(String userName) {
        jdbcTemplate.update("DELETE FROM user WHERE user_name = ?", userName);
    }

    @Override
    public void removeRole(String userName, String roleName) {
        Integer userId = findUserIdByUserName(userName);
        if(userId != null){
            Integer roleId = findRoleIdByRoleName(roleName);
            if(roleId != null){
                jdbcTemplate.update(
                        "DELETE FROM user_role WHERE user_id = ? AND role_id = ?",
                        userId, roleId);
            }
        }
    }

    @Override
    public void removeRoles(String userName) {
        Integer userId = findUserIdByUserName(userName);
        if(userId != null){
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id = ?", userId);
        }
    }

    @Override
    public List<String> getAllUserNames(String roleName) {
        Integer roleId = findRoleIdByRoleName(roleName);
        return jdbcTemplate.queryForList(
                "SELECT user_name FROM user INNER JOIN user_role " +
                        "ON user_role.role_id = ? AND user.id = user_role.user_id",
                String.class, roleId);
    }

    @Override
    public List<String> getAllRoleNames(String userName) {
        Integer userId = findUserIdByUserName(userName);
        String query = "SELECT role_name FROM role INNER JOIN user_role " +
                "ON user_role.user_id = :user_id AND role.id = user_role.role_id";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        return namedParameterJdbcTemplate.queryForList(query, params, String.class);
    }

    @Override
    public void updatePassword(String userName, String password) {
        String update = "UPDATE user SET "
                + "password = :password WHERE user_name = :user_name";
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", userName);
        params.put("password", encoder.encode(password));
        namedParameterJdbcTemplate.update(update, params);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        String stored_password = getPassword(userName);
        return encoder.matches(password, stored_password);
    }

    @Override
    public String getPassword(String userName) {
        return jdbcTemplate.queryForObject(
                "SELECT password FROM user WHERE user_name = ?",
                String.class, userName);
    }

}
