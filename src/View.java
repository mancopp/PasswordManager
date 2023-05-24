import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JPanel cards;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JButton regRegisterButton;
    private JButton regBackButton;
    private CardLayout cardLayout = new CardLayout();
    public PasswordViewPanel passwordViewCard = new PasswordViewPanel();
    public PasswordListPanel passwordListCard = new PasswordListPanel();
    public PasswordHistoryPanel passwordHistoryCard = new PasswordHistoryPanel();
    public RegisterPanel registerCard = new RegisterPanel();

    public View() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create card layout and panel
        cards = new JPanel(cardLayout);

        // Login card
        JPanel loginCard = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginCard.add(usernameLabel);
        loginCard.add(usernameField);
        loginCard.add(passwordLabel);
        loginCard.add(passwordField);
        loginCard.add(loginButton);
        loginCard.add(registerButton);

        // Registration card
//        registerCard = new JPanel();
//        JLabel regUsernameLabel = new JLabel("Username:");
//        JLabel regPasswordLabel = new JLabel("Password:");
//        regUsernameField = new JTextField(10);
//        regPasswordField = new JPasswordField(10);
//        regRegisterButton = new JButton("Register");
//        regBackButton = new JButton("Back");

//        registerCard.add(regUsernameLabel);
//        registerCard.add(regUsernameField);
//        registerCard.add(regPasswordLabel);
//        registerCard.add(regPasswordField);
//        registerCard.add(regRegisterButton);
//        registerCard.add(regBackButton);

        // Add cards to panel
        cards.add(loginCard, "login");
        cards.add(registerCard, "register");
        cards.add(passwordListCard, "passwordList");
        cards.add(passwordViewCard, "passwordView");
        cards.add(passwordHistoryCard, "passwordHistory");

        frame.getContentPane().add(cards);
        frame.pack();
        frame.setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRegUsername() {
        return regUsernameField.getText();
    }

    public String getRegPassword() {
        return new String(regPasswordField.getPassword());
    }

    public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterButtonListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showPasswordList(User data) {
        frame.setTitle(data.getUsername() + "'s passwords");
        passwordListCard.setData(data);
        cardLayout.show(cards, "passwordList");
    }

    public void showPasswordView(PasswordObject data) {
        frame.setTitle("Password data for \"" + data.getLabel() + "\"");
        passwordViewCard.setData(data);
        cardLayout.show(cards, "passwordView");
    }
    public void showPasswordHistory(PasswordObject data) {
        frame.setTitle("Password History for \"" + data.getLabel() + "\"");
        passwordHistoryCard.setData(data.getUsedDates());
        cardLayout.show(cards, "passwordHistory");
    }

    public void showRegistrationForm() {
        frame.setTitle("Register");
        cardLayout.show(cards, "register");
    }

    public void showLogin() {
        frame.setTitle("Login");
        cardLayout.show(cards, "login");
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getCopyPasswordButton() {
        return passwordViewCard.getPasswordCopyButton();
    }

    public JButton getCopyUsernameButton() {
        return passwordViewCard.getUsernameCopyButton();
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
