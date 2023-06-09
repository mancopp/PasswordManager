package View;

import Model.PasswordObject;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.util.List;

public class PasswordListPanel extends JPanel {
    private String selectedSortMethod = "By Date";
    private Map<String, PasswordObject> passwords = new HashMap<String, PasswordObject>();
    private JList<String> list;
    private JButton addButton;

    public PasswordListPanel(){
        setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String label : passwords.keySet()) {
            listModel.addElement(label);
        }
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        JComboBox<String> sortMethodComboBox = new JComboBox<>(new String[]{"By Date", "By Label", "By Times Used"});
        sortMethodComboBox.addActionListener(e -> {
            selectedSortMethod = (String) sortMethodComboBox.getSelectedItem();
            sortPasswords(selectedSortMethod);
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(sortMethodComboBox, BorderLayout.CENTER);

        addButton = new JButton("+");
        topPanel.add(addButton, BorderLayout.LINE_END);

        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    public void setData(User user) {
        passwords = user.getPasswords();
        sortPasswords(selectedSortMethod);
    }

    public void addListDoubleClickListener(MouseAdapter mouseAdapter) {
        list.addMouseListener(mouseAdapter);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    private void sortPasswords(String sortMethod) {
        List<Map.Entry<String, PasswordObject>> sortedList = new ArrayList<>(passwords.entrySet());
        switch (sortMethod) {
            case "By Date":
                sortedList.sort(Comparator.comparing(entry -> {
                    Date lastUsed = ((Map.Entry<?, PasswordObject>) entry).getValue().getLastUsed();
                    return lastUsed != null ? lastUsed : new Date(0);
                }).reversed());
                break;
            case "By Label":
                sortedList.sort(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER));
                break;
            case "By Times Used":
                sortedList.sort(Comparator.comparingInt(entry -> ((Map.Entry<?, PasswordObject>) entry).getValue().getTimesUsed()).reversed());
                break;
            default:
                Collections.sort(sortedList, Comparator.comparing(entry -> entry.getValue().getDateCreated()));
        }

        DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
        listModel.clear();
        for (Map.Entry<String, PasswordObject> entry : sortedList) {
            listModel.addElement(entry.getKey());
        }
    }

}