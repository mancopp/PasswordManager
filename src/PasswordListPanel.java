import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PasswordListPanel extends JPanel {
    private Map<String, PasswordObject> passwords;
    private JList<String> list;
    private ListSelectionListener listSelectionListener;

    public PasswordListPanel(){}

    public void addListSelectionListener(ListSelectionListener listSelectionListener) {
        this.listSelectionListener = listSelectionListener;
    }

    public void setData(User user) {
        Map<String, PasswordObject> passwords = user.getPasswords();
        this.passwords = passwords;
        setLayout(new BorderLayout());

        // Create the list model and JList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String label : passwords.keySet()) {
            listModel.addElement(label);
        }
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create the scroll pane and add the list to it
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        // Add list selection listener to display the password
        list.addListSelectionListener(listSelectionListener);

        // Create the sort method selection box
        JComboBox<String> sortMethodComboBox = new JComboBox<>(new String[]{"By Date", "By Label", "By Times Used"});
        sortMethodComboBox.addActionListener(e -> {
            String selectedSortMethod = (String) sortMethodComboBox.getSelectedItem();
            sortPasswords(selectedSortMethod);
        });

        // Add components to the panel
        add(scrollPane, BorderLayout.CENTER);
        add(sortMethodComboBox, BorderLayout.NORTH);
    }

    private void sortPasswords(String sortMethod) {
        List<Map.Entry<String, PasswordObject>> sortedList = new ArrayList<>(passwords.entrySet());
        switch (sortMethod) {
            case "By Date":
                Collections.sort(sortedList, Comparator.comparing(entry -> entry.getValue().getDateCreated()));
                break;
            case "By Label":
                Collections.sort(sortedList, Comparator.comparing(Map.Entry::getKey));
                break;
            case "By Times Used":
                Collections.sort(sortedList, Comparator.comparingInt(entry -> entry.getValue().getTimesUsed()));
                break;
        }

        DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
        listModel.clear();
        for (Map.Entry<String, PasswordObject> entry : sortedList) {
            listModel.addElement(entry.getKey());
        }
    }
}