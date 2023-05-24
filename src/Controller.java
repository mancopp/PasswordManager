import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        view.passwordListCard.addListDoubleClickListener(new PasswordListPanelListDoubleClickListener());

        view.passwordViewCard.addCopyButtonListener(new CopyActionListener());
        view.passwordViewCard.addPasswordToggleButtonListener(new PasswordViewToggleButtonListener());
        view.passwordViewCard.addBackButtonListener(new ViewPanelBackButtonListener());
        view.passwordViewCard.addHistoryButtonListener(new HistoryButtonListener());

        view.passwordHistoryCard.addBackButtonListener(new HistoryPanelBackButtonListener());

        view.showLogin();
    }

    private class PasswordListPanelListDoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
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

    private class HistoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showPasswordHistory(model.getPasswordSession());
        }
    }

    private class ViewPanelBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showPasswordList(model.getUserSession());
        }
    }

    private class HistoryPanelBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showPasswordView(model.getPasswordSession());
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
