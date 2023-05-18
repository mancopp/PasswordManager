import java.io.Serializable;
import java.util.Date;
class PasswordObject implements Serializable {
    private String label;
    private String username;
    private String password;
    private Date dateCreated;
    private int timesUsed;

    public PasswordObject(String label, String username, String password) {
        this.label = label;
        this.username = username;
        this.password = password;
        this.dateCreated = new Date();
        this.timesUsed = 0;
    }

    public String getLabel() {
        return label;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        timesUsed++;
        return password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getTimesUsed() {
        return timesUsed;
    }
}