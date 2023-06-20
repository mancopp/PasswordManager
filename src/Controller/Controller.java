package Controller;

import Model.Model;
import Model.PasswordObject;
import View.View;

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

        view.registerCard.addCancelButtonListener(new RegisterCancelButtonListener());
        view.registerCard.addRegisterButtonListener(new RegisterRegisterButtonListener());

        view.passwordListCard.addListDoubleClickListener(new PasswordListPanelListDoubleClickListener());
        view.passwordListCard.addAddButtonListener(new ListPanelAddButtonListener());

        view.passwordViewCard.addCopyButtonListener(new CopyActionListener());
        view.passwordViewCard.addPasswordToggleButtonListener(new PasswordViewToggleButtonListener());
        view.passwordViewCard.addBackButtonListener(new ViewPanelBackButtonListener());
        view.passwordViewCard.addEditButtonListener(new ViewPanelEditButtonListener());
        view.passwordViewCard.addHistoryButtonListener(new HistoryButtonListener());

        view.passwordHistoryCard.addBackButtonListener(new HistoryPanelBackButtonListener());

        view.passwordFormCard.addAddButtonListener(new AddPanelAddButtonListener());
        view.passwordFormCard.addCancelButtonListener(new AddPanelCancelButtonListener());

        view.settingsCard.addGenerateButtonListener(new SettingsGenerateButtonListener());
        view.settingsCard.addSaveButtonListener(new SettingsSaveButtonListener());

        if(model.encryptionDataExists()){
            model.loadEncryptionData();
            model.loadUsers();
            view.setFrameVisible(true);
            view.showLogin();
        }else{
            if(model.usersDataExists()){
                JOptionPane.showMessageDialog(null, "Seems like you have stored passwords in the past, but there is no data.json file with you secret key.\nIf you have it, please move it to " + model.DIRECTORY_PATH + ".\nIf you want to start over (You'll lose your data!), just delete the file\n" + model.DIRECTORY_PATH + "/users.dat" , "Secret key was not found", JOptionPane.ERROR_MESSAGE);
            }else{
                view.setFrameVisible(true);
                view.showSettings();
            }
        }
    }

    private class PasswordListPanelListDoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                Object source = e.getSource();

                if (source instanceof JList) {
                    JList<String> list = (JList<String>) source;
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

    private class SettingsGenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String key = model.generateSecretKey(256);
            view.settingsCard.setSecretKeyTextField(key);
        }
    }
        private class SettingsSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String algorithm = view.settingsCard.getAlgorithmDropdownText();
            String key = view.settingsCard.getSecretKeyTextFieldText();

            model.setEncryption(algorithm, key);
            view.showRegistrationForm();
        }
    }
    private class ListPanelAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.passwordFormCard.setAddMode();
            view.showPasswordForm("Add new password data");
        }
    }

    private class AddPanelAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String label = view.passwordFormCard.getLabel().getText();
            String username = view.passwordFormCard.getUsernameField().getText();
            String password = new String(view.passwordFormCard.getPasswordField().getPassword());

            if (label.isEmpty()) {
                view.showErrorMessage("Label is required");
                return;
            }

            if (username.isEmpty()) {
                view.showErrorMessage("Username is required");
                return;
            }

            if (password.isEmpty()) {
                view.showErrorMessage("Password is required");
                return;
            }

            String oldLabel = view.passwordFormCard.getOldLabel();
            if(oldLabel != null){
                model.updateCurrentUserPassword(oldLabel, label, username, password);
                view.showSuccessMessage("Password record updated successfully");
            }else{
                if (model.getUserSession().isPasswordLabelExists(label)) {
                    view.showErrorMessage("Password record with that label already exists");
                    return;
                }

                model.addPasswordRecordToCurrentUser(label, username, password);
                view.showSuccessMessage("Password record added successfully");
            }
            view.showPasswordList(model.getUserSession());
        }
    }

    private class AddPanelCancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showPasswordList(model.getUserSession());
        }
    }
    private class RegisterCancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showLogin();
        }
    }

    private class RegisterRegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.registerCard.getUsernameField().getText();
            String password = new String(view.registerCard.getPasswordField().getPassword());
            String confirmPassword = new String(view.registerCard.getConfirmPasswordField().getPassword());

            if (username.isEmpty()) {
                view.showErrorMessage("Username is required");
                return;
            }

            if (password.isEmpty()) {
                view.showErrorMessage("Password is required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.showErrorMessage("Passwords do not match");
                return;
            }

            if (model.isUserExists(username)) {
                view.showErrorMessage("Username already exists");
                return;
            }

            model.registerUser(username, password);
            view.showSuccessMessage("Registration successful!\nYou may login now.");
            view.showLogin();
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

    private class ViewPanelEditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PasswordObject currentPassword = model.getPasswordSession();
            view.passwordFormCard.setEditMode(currentPassword.getLabel(), currentPassword.getUsername(), currentPassword.getPassword());
            view.showPasswordForm("Edit password data");
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
