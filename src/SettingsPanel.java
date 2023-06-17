import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    public String getAlgorithmDropdownText() {
        return (String) algorithmDropdown.getSelectedItem();
    }

    private JComboBox<String> algorithmDropdown;

    public String getSecretKeyTextFieldText() {
        return secretKeyTextField.getText();
    }

    private JTextField secretKeyTextField;
    private JButton generateButton = new JButton("Generate");
    private JButton saveButton = new JButton("Save");

    public SettingsPanel() {
        setLayout(new GridLayout(3, 2));

        JLabel algorithmLabel = new JLabel("Encryption algorithm:");
        algorithmDropdown = new JComboBox<>(new String[]{"AES", "Blowfish"});

        JLabel secretKeyLabel = new JLabel("Secret Key:");
        secretKeyTextField = new JTextField();
        secretKeyTextField.setEditable(false);

        add(algorithmLabel);
        add(algorithmDropdown);
        add(secretKeyLabel);
        add(secretKeyTextField);
        add(new JLabel());
        add(generateButton);
        add(saveButton);
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
