package ohtu.services;

import ohtu.domain.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int PASSWORD_MIN_LENGTH = 8;

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        return !validateUsername(username) || !validatePassword(password);
    }

    private boolean validateUsername(String username) {
        return username.length() >= USERNAME_MIN_LENGTH
            && hasOnlyAlphabets(username);
    }

    private boolean validatePassword(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH
            && containsDigits(password)
            && containsSpecialSymbols(password);
    }

    private boolean hasOnlyAlphabets(String str) {
        return matchesRegex(str, "^[a-z]+$");
    }

    private boolean containsDigits(String str) {
        return matchesRegex(str, "\\d");
    }

    private boolean containsSpecialSymbols(String str) {
        return matchesRegex(str, "[^a-zA-Z\\d]");
    }

    private boolean matchesRegex(String string, String regexp) {
        Matcher matcher = Pattern.compile(regexp).matcher(string);
        return matcher.find();
    }
}
