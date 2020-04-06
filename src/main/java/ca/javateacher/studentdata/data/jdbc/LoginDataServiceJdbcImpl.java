package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.data.LoginDataRepository;
import ca.javateacher.studentdata.data.LoginDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginDataServiceJdbcImpl implements LoginDataService {

    private LoginDataRepository loginDataRepository;

    public LoginDataServiceJdbcImpl(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public boolean userExists(String userName) {
        return loginDataRepository.userExists(userName);
    }

    @Override
    public void insertUser(String userName, String password) {
        loginDataRepository.insertUser(userName, password);
    }

    @Override
    public void insertRole(String userName, String roleName) {
        loginDataRepository.insertRole(userName, roleName);
    }

    @Override
    public void removeUser(String userName) {
        loginDataRepository.removeUser(userName);
    }

    @Override
    public void removeRole(String userName, String roleName) {
        loginDataRepository.removeRole(userName, roleName);
    }

    @Override
    public void removeRoles(String userName) {
        loginDataRepository.removeRoles(userName);
    }

    @Override
    public List<String> getAllUserNames(String roleName) {
        return loginDataRepository.getAllUserNames(roleName);
    }

    @Override
    public List<String> getAllRoleNames(String userName) {
        return loginDataRepository.getAllRoleNames(userName);
    }

    @Override
    public void updatePassword(String userName, String password) {
        loginDataRepository.updatePassword(userName, password);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        return loginDataRepository.checkPassword(userName, password);
    }

    @Override
    public String getPassword(String userName) {
        return loginDataRepository.getPassword(userName);
    }
}
