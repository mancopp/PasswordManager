import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterPanel() {
        setLayout(new BorderLayout());

        // Create top panel with cancel button
        JPanel topPanel = new JPanel();
        cancelButton = new JButton("Cancel");
        topPanel.add(cancelButton);

        // Create center panel with registration content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        centerPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(usernameField, gbc);
        gbc.gridy++;
        centerPanel.add(passwordField, gbc);
        gbc.gridy++;
        centerPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        centerPanel.add(registerButton, gbc);

        // Add panels to the main panel
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void addCancelButtonListener(ActionListener listener){
        cancelButton.addActionListener(listener);
    }

    public void addRegisterButtonListener(ActionListener listener){
        registerButton.addActionListener(listener);
    }
}
