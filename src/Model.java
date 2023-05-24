import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<User> userList;

    public User getUserSession() {
        return userSession;
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }

    private User userSession;

    public PasswordObject getPasswordSession() {
        return passwordSession;
    }

    public void setPasswordSession(PasswordObject passwordSession) {
        passwordSession.incrementTimesUsed();
        this.passwordSession = passwordSession;
    }

    private PasswordObject passwordSession;

    public Model() {
        userList = new ArrayList<>();
        User usr = new User("mncpp", "test");
        usr.addPassword("Google", "Mancopp", "googlePass1");
        usr.addPassword("Facebook", "Mancopp", "facebookPass1");
        usr.addPassword("Instagram", "Mancopp", "instagramPass1");
        usr.addPassword("Youtube", "Mancopp", "youtubePass12youtubePass12youtubePass12");
        userList.add(usr);
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.authenticate(username, password)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        userList.add(user);
    }

    // Other methods for managing user data
}
