import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PasswordObject implements Serializable {
    private String label;
    private String username;
    private String password;
    private Date dateCreated;

    // List of dates used and possibility to check use history
    private List<Date> usedDates;
    private int timesUsed;

    public PasswordObject(String label, String username, String password) {
        this.usedDates = new ArrayList<Date>();
        this.label = label;
        this.username = username;
        this.password = password;
        this.dateCreated = new Date();
        this.timesUsed = 0;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getLabel() {
        return label;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void incrementTimesUsed() {
        timesUsed++;
        usedDates.add(new Date());
    }
}