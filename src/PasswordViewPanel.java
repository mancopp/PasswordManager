import javax.swing.*;

public class PasswordViewPanel extends JPanel {
    private JLabel label;
    private JLabel username;
    private JLabel password;
    public PasswordViewPanel(){
        label = new JLabel("-");
        username = new JLabel("-");
        password = new JLabel("-");
        add(label);
        add(username);
        add(password);
    }

    public void setData(PasswordObject password){
        this.label.setText(password.getLabel());
        this.username.setText(password.getUsername());
        this.password.setText(password.getPassword());
    }
}