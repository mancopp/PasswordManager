import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordAddPanel extends JPanel {
    private JButton addButton;
    private JTextField usernameField;

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getLabel() {
        return label;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private JButton cancelButton;
    private JTextField label;
    private JPasswordField passwordField;

    public PasswordAddPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        cancelButton = new JButton("Cancel");
        topPanel.add(cancelButton, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        label = new JTextField();
        formPanel.add(new JLabel("Label:"));
        formPanel.add(label);

        usernameField = new JTextField();
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);

        passwordField = new JPasswordField();
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        addButton = new JButton("Add");

        formPanel.add(new JLabel());
        formPanel.add(addButton);

        contentPanel.add(formPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

}