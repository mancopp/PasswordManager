import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.*;

public class Model {

    public static final String DIRECTORY_PATH = System.getProperty("user.home") + "/Documents/PasswordManager";
    private List<User> userList = new ArrayList<>();

    public User getUserSession() {
        return userSession;
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }

    private User userSession;

    private String encryption_algorithm = "AES";
    private String secretKey;

    private static class KeyAlg {
        private String key;
        private String alg;

        public KeyAlg(String key, String alg) {
            this.key = key;
            this.alg = alg;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }
    }

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
        //loadUsers();
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

    public boolean encryptionDataExists() {
        File file = new File(DIRECTORY_PATH + "/data.json");
        return file.exists();
    }

    public boolean usersDataExists() {
        File file = new File(DIRECTORY_PATH + "/users.dat");
        return file.exists();
    }

    public void loadUsers() {
        try {
            File file = new File(DIRECTORY_PATH + "/users.dat");
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

            try (FileOutputStream fos = new FileOutputStream(DIRECTORY_PATH + "/users.dat");
                 CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                 ObjectOutputStream oos = new ObjectOutputStream(cos)) {

                oos.writeObject(userList);
            }
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void saveEncryptionData() {
        KeyAlg keyAlg = new KeyAlg(secretKey, encryption_algorithm);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(keyAlg);

        try (FileWriter writer = new FileWriter(DIRECTORY_PATH + "/data.json")) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEncryptionData() {
        try (FileReader reader = new FileReader(DIRECTORY_PATH + "/data.json")) {
            Gson gson = new GsonBuilder().create();
            KeyAlg keyAlg = gson.fromJson(reader, KeyAlg.class);

            secretKey = keyAlg.getKey();
            encryption_algorithm = keyAlg.getAlg();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cipher getCipher(int cipherMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(), encryption_algorithm);
            Cipher cipher = Cipher.getInstance(encryption_algorithm);
            cipher.init(cipherMode, secretKey);
            return cipher;
    }

    public String generateSecretKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLength / 8];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public void setEncryption(String alg, String key){
        encryption_algorithm = alg;
        secretKey = key;
        saveEncryptionData();
    }

    public void addPasswordRecordToCurrentUser(String label, String username, String password) {
        User user = getUserSession();
        user.addPassword(label, username, password);
        saveUsers();
    }
}
