package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import model.observer.UserObserver;
import shared.Navigation;

public class UserPageView extends PageView implements UserObserver {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton createButton, updateButton, deleteButton;

    public UserPageView() {
        super("User Management");
        setVisible(true);
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table setup
        String[] columns = { "User ID", "Name", "Email", "Phone", "Role" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        JButton homeButton = new JButton("Home");

        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(homeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void updateTable(List<User> users) {
        tableModel.setRowCount(0);
        for (User user : users) {
            tableModel.addRow(new Object[] {
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRole()
            });
        }
    }

    public String getSelectedUserId() {
        int row = userTable.getSelectedRow();
        if (row == -1)
            return null;
        return (String) tableModel.getValueAt(row, 0);
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    @Override
    public void updateUser(List<User> users) {
        updateTable(users);
    }
}
