import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private Map<String, PasswordObject> passwords = new HashMap<>();

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void addPassword(String label, String username, String password){
        passwords.put(label, new PasswordObject(label, username, password));
    }

    public PasswordObject getPasswordByLabel(String label) {
        return passwords.get(label);
    }


    public boolean authenticate(String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }

    public Map<String, PasswordObject> getPasswords(){
        return passwords;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPasswordLabelExists(String label) {
        return passwords.containsKey(label);
    }
}
