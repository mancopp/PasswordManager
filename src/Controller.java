import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;
    private User user;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addPasswordListPanelListSelectionListener(new PasswordListPanelListSelectionListener());
        view.addLoginButtonListener(new LoginButtonListener());
        view.addRegisterButtonListener(new RegisterButtonListener());
        view.addRegRegisterButtonListener(new RegRegisterButtonListener());
        view.addRegBackButtonListener(new RegBackButtonListener());

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
                        PasswordObject passwordObject = user.getPasswordByLabel((String) selectedData);
                        view.showPasswordView(passwordObject);
                        // Process the selected data as needed
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
                user = model.getUserByUsername(username);
                view.showPasswordList(user);
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
}
