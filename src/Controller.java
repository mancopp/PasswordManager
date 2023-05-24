import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addLoginButtonListener(new LoginButtonListener());
        view.addRegisterButtonListener(new RegisterButtonListener());
        view.addRegRegisterButtonListener(new RegRegisterButtonListener());
        view.addRegBackButtonListener(new RegBackButtonListener());

        view.passwordListCard.addListSelectionListener(new PasswordListPanelListSelectionListener());
        view.passwordViewCard.addCopyButtonListener(new CopyActionListener());
        view.passwordViewCard.addPasswordToggleButtonListener(new PasswordViewToggleButtonListener());
        view.passwordViewCard.addBackButtonListener(new BackButtonListener());

        view.showLogin();
    }

    private class PasswordListPanelListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Object source = e.getSource();

                if (source instanceof JList) {
                    JList list = (JList) source;
                    int selectedIndex = list.getSelectedIndex();

                    if (selectedIndex != -1) {
                        Object selectedData = list.getSelectedValue();
                        model.setPasswordSession(model.getUserSession().getPasswordByLabel((String) selectedData));
                        view.showPasswordView(model.getPasswordSession());
                    }
                }
            }
        }
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (model.authenticateUser(username, password)) {
                model.setUserSession(model.getUserByUsername(username));
                view.showPasswordList(model.getUserSession());
            } else {
                view.showErrorMessage("Invalid username or password");
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showRegistrationForm();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showPasswordList(model.getUserSession());
        }
    }

    private class CopyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = null;
            JButton sourceButton = (JButton) e.getSource();

            if (sourceButton == view.getCopyPasswordButton()) {
                text = model.getPasswordSession().getPassword();
            } else if (sourceButton == view.getCopyUsernameButton()) {
                text = model.getPasswordSession().getUsername();
            }
            copyStringToClipboard(text);
        }
    }

    private class PasswordViewToggleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.passwordViewCard.handleToggleButtonPressed();
        }
    }

    private class RegRegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getRegUsername();
            String password = view.getRegPassword();

            model.registerUser(username, password);
            view.showLogin();
            view.showErrorMessage("Registration successful");
        }
    }

    private class RegBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showLogin();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Controller controller = new Controller(model, view);
        });
    }

    private void copyStringToClipboard(String content) {
        StringSelection selection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        view.showErrorMessage("Copied to clipboard.");
    }
}
