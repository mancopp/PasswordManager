import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Model {

    private static final String DIRECTORY_PATH = System.getProperty("user.home") + "/Documents/PasswordManager";
    private static final String FILE_PATH = DIRECTORY_PATH + "/users.dat";
    private static final String SECRET_KEY = "ThisIsASecretKey";
    private List<User> userList = new ArrayList<>();

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
        saveUsers();
        this.passwordSession = passwordSession;
    }

    private PasswordObject passwordSession;

    public Model() {
        loadUsers();
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : userList) {
            if (user.authenticate(username, password)) {
                return true;
            }
        }
        return false;
    }

    public void updateCurrentUserPassword(String label, String newLabel, String newUsername, String newPassword) {
        userSession.updatePassword(label, newLabel, newUsername, newPassword);
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
        saveUsers();
    }

    public boolean isUserExists(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void loadUsers() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

            try (FileInputStream fis = new FileInputStream(file);
                 CipherInputStream cis = new CipherInputStream(fis, cipher);
                 ObjectInputStream ois = new ObjectInputStream(cis)) {

                userList = (List<User>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }


    public void saveUsers() {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
                 CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                 ObjectOutputStream oos = new ObjectOutputStream(cos)) {

                oos.writeObject(userList);
            }
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    private Cipher getCipher(int cipherMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode, secretKey);
        return cipher;
    }

    public void addPasswordRecordToCurrentUser(String label, String username, String password) {
        User user = getUserSession();
        user.addPassword(label, username, password);
        saveUsers();
    }
}
