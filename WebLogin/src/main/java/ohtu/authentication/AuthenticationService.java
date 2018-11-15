package ohtu.authentication;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

public class AuthenticationService {

    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int PASSWORD_MIN_LENGTH = 8;

    private UserDao userDao;

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

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }

        if (username.length() < 3) {
            status.addError("username should have at least 3 characters");
        }

        if (!hasOnlyAlphabets(username)) {
            status.addError("username should only contain letters a-z");
        }

        if (password.length() < 8) {
            status.addError("password should have at least 8 characters");
        }

        if (!containsDigits(password) && !containsSpecialSymbols(password)) {
            status.addError("password should have numbers or special symbols");
        }

        if (!password.equals(passwordConfirmation)) {
            status.addError("password and password confirmation do not match");
        }

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }

        return status;
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
