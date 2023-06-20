package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PasswordHistoryPanel extends JPanel {
    private JButton backButton;
    private JLabel titleLabel;
    private DefaultListModel<String> historyListModel;
    private JList<String> historyList;

    public PasswordHistoryPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        backButton = new JButton("Back");
        topPanel.add(backButton, BorderLayout.WEST);

        titleLabel = new JLabel("Password History");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        historyListModel = new DefaultListModel<>();
        historyList = new JList<>(historyListModel);

        JScrollPane scrollPane = new JScrollPane(historyList);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void setData(List<Date> dates) {
        historyListModel.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Date date : dates) {
            historyListModel.addElement(dateFormat.format(date));
        }
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
