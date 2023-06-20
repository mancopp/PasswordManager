package Model;

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

    public PasswordObject addPassword(String label, String username, String password){
        PasswordObject pwd = new PasswordObject(label, username, password);
        passwords.put(label, pwd);
        return pwd;
    }

    public void updatePassword(String label, String newLabel, String newUsername, String newPassword){
        PasswordObject passwordObject = getPasswordByLabel(label);

        passwordObject.setLabel(newLabel);
        passwordObject.setUsername(newUsername);
        passwordObject.setPassword(newPassword);

        passwords.put(newLabel, passwordObject);
        if (!newLabel.equals(label)) {
            passwords.remove(label);
        }
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
