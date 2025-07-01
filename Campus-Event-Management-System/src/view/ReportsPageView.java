package view;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.EventRegistration;
import model.observer.RegistrationObserver;

public class ReportsPageView extends PageView implements RegistrationObserver {

    private JTable registrationTable;
    private DefaultTableModel tableModel;

    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;

    public ReportsPageView() {
        super("Event Registrations");
        setVisible(true);
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {
                "Registration ID", "User ID", "Event ID", "Date", "Status", "Payment Amount"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable directly
            }
        };

        registrationTable = new JTable(tableModel);
        registrationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(registrationTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void updateTable(List<EventRegistration> registrations) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tableModel.setRowCount(0);
        for (EventRegistration reg : registrations) {
            tableModel.addRow(new Object[] {
                    reg.getRegistrationId(),
                    reg.getUserId(),
                    reg.getEventId(),
                    sdf.format(reg.getRegistrationDate()),
                    reg.getStatus(),
                    String.format("RM%.2f", reg.getPaymentAmount())
            });
        }
    }

    public int getSelectedRow() {
        return registrationTable.getSelectedRow();
    }

    public String getSelectedRegistrationId() {
        int row = getSelectedRow();
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

    public JButton getRefreshButton() {
        return refreshButton;
    }

    @Override
    public void update(List<EventRegistration> registrations) {
        updateTable(registrations);
    }
}
