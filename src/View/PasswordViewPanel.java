package View;

import Model.PasswordObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordViewPanel extends JPanel {
    private final char ECHO_CHAR = '\u2022';
    private JButton historyButton;
    private JLabel timesUsed;
    private JButton backButton;
    private JButton editButton;
    private JLabel label;
    private JLabel username;
    private JPasswordField passwordField;
    private JToggleButton passwordToggleButton;

    public JButton getPasswordCopyButton() {
        return passwordCopyButton;
    }

    public JButton getUsernameCopyButton() {
        return usernameCopyButton;
    }

    private JButton passwordCopyButton;
    private JButton usernameCopyButton;

    public PasswordViewPanel(){
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Back");
        editButton = new JButton("Edit");
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(editButton, BorderLayout.EAST);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        label = new JLabel();
        JPanel contentRow1Panel = new JPanel(new BorderLayout());
        contentRow1Panel.add(new JLabel("Label:"), BorderLayout.WEST);
        contentRow1Panel.add(label, BorderLayout.CENTER);
        contentPanel.add(contentRow1Panel, gbc);

        username = new JLabel();
        usernameCopyButton = new JButton("Copy");
        JPanel contentRow2Panel = new JPanel(new BorderLayout());
        contentRow2Panel.add(new JLabel("Username:"), BorderLayout.WEST);
        contentRow2Panel.add(username, BorderLayout.CENTER);
        contentRow2Panel.add(usernameCopyButton, BorderLayout.EAST);
        contentPanel.add(contentRow2Panel, gbc);

        JPanel contentRow3Panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        passwordField = new JPasswordField();
        passwordField.setEditable(false);
        passwordField.setEchoChar(ECHO_CHAR);

        passwordCopyButton = new JButton("Copy");
        passwordToggleButton = new JToggleButton("Show");
        buttonPanel.add(passwordCopyButton);
        buttonPanel.add(passwordToggleButton);
        contentRow3Panel.add(new JLabel("Password:"), BorderLayout.WEST);
        contentRow3Panel.add(passwordField, BorderLayout.CENTER);
        contentRow3Panel.add(buttonPanel, BorderLayout.EAST);
        contentPanel.add(contentRow3Panel, gbc);

        timesUsed = new JLabel();
        historyButton = new JButton("History");
        JPanel contentRow4Panel = new JPanel(new BorderLayout());
        contentRow4Panel.add(new JLabel("Times used:"), BorderLayout.WEST);
        contentRow4Panel.add(timesUsed, BorderLayout.CENTER);
        contentRow4Panel.add(historyButton, BorderLayout.EAST);
        contentPanel.add(contentRow4Panel, gbc);

        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void setData(PasswordObject password){

        label.setText(password.getLabel());
        username.setText(password.getUsername());
        passwordField.setText(password.getPassword());
        timesUsed.setText(String.valueOf(password.getTimesUsed()));

        passwordField.setEchoChar(ECHO_CHAR);
        passwordToggleButton.setSelected(false);
        passwordToggleButton.setText("Show");
    }

    public String getDisplayedPassword(){
        return new String(passwordField.getPassword());
    }

    public void addPasswordToggleButtonListener(ActionListener listener){
        passwordToggleButton.addActionListener(listener);
    }

    public void handleToggleButtonPressed() {
        if (passwordToggleButton.isSelected()) {
            passwordField.setEchoChar((char) 0);
            passwordToggleButton.setText("Hide");
        } else {
            passwordField.setEchoChar(ECHO_CHAR);
            passwordToggleButton.setText("Show");
        }
    }

    public void addCopyButtonListener(ActionListener listener) {
        passwordCopyButton.addActionListener(listener);
        usernameCopyButton.addActionListener(listener);
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addHistoryButtonListener(ActionListener listener) {
        historyButton.addActionListener(listener);
    }
}