import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PasswordObject implements Serializable {
    private String label;
    private String username;
    private String password;
    private Date dateCreated;
    private List<Date> usedDates;
    private Date lastUsed;
    private int timesUsed;

    public PasswordObject(String label, String username, String password) {
        this.label = label;
        this.username = username;
        this.password = password;
        this.dateCreated = new Date();
        this.usedDates = new ArrayList<>();
        this.timesUsed = 0;
    }

    public String getLabel() {
        return label;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public List<Date> getUsedDates() {
        return usedDates;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void incrementTimesUsed() {
        Date date = new Date();
        timesUsed++;
        usedDates.add(date);
        lastUsed = date;
    }
}
