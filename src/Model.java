import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<User> userList;

    public Model() {
        userList = new ArrayList<>();
        User usr = new User("mncpp", "test");
        usr.addPassword("google", "mancopp", "hello");
        usr.addPassword("facebook", "mancopp", "hello");
        usr.addPassword("instagram", "mancopp", "hello");
        usr.addPassword("youtube", "mancopp", "hello");
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
