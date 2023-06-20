package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JComboBox<String> algorithmDropdown;
    private JTextField secretKeyTextField;
    private JButton generateButton;
    private JButton saveButton;

    public SettingsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel algorithmLabel = new JLabel("Encryption algorithm:");
        algorithmDropdown = new JComboBox<>(new String[]{"AES", "Blowfish"});

        JLabel secretKeyLabel = new JLabel("Secret Key:");
        secretKeyTextField = new JTextField();
        secretKeyTextField.setEditable(false);

        generateButton = new JButton("Generate");
        saveButton = new JButton("Save");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(algorithmLabel, gbc);

        gbc.gridx = 1;
        add(algorithmDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(secretKeyLabel, gbc);

        gbc.gridx = 1;
        add(secretKeyTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(generateButton, gbc);

        gbc.gridy = 3;
        add(saveButton, gbc);
    }

    public String getAlgorithmDropdownText() {
        return (String) algorithmDropdown.getSelectedItem();
    }
    public String getSecretKeyTextFieldText() {
        return secretKeyTextField.getText();
    }
    public void addGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    public void setSecretKeyTextField(String t){
        secretKeyTextField.setText(t);
    }
}
