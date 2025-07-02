package view;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Event;
import model.EventRegistration;
import model.User;
import model.manager.EventManager;
import model.manager.RegistrationManager;
import model.manager.UserManager;
import model.observer.EventObserver;
import model.observer.RegistrationObserver;
import model.observer.UserObserver;
import shared.Navigation;

public class ReportsPageView extends PageView implements RegistrationObserver, UserObserver, EventObserver {

    private JTable registrationTable;
    private DefaultTableModel tableModel;

    private JButton deleteButton;

    public ReportsPageView() {
        super("Event Registrations");
        setVisible(true);
    }

    // Some bug have problem
    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {
                "Registration ID", "User ID", "User Name", "Event ID", "Event Name", "Date", "Status", "Payment Amount"
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
        deleteButton = new JButton("Delete");
        JButton homeButton = new JButton("Home");

        buttonPanel.add(deleteButton);
        buttonPanel.add(homeButton);
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void updateTable(List<EventRegistration> registrations) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tableModel.setRowCount(0);

        UserManager userManager = UserManager.getInstance();
        EventManager eventManager = EventManager.getInstance();

        for (EventRegistration reg : registrations) {
            String userId = reg.getUserId();
            String eventId = reg.getEventId();

            String userName = userManager.getUserFromId(userId) != null
                    ? userManager.getUserFromId(userId).getName()
                    : "Not Found";

            String eventName = eventManager.getEventById(eventId) != null
                    ? eventManager.getEventById(eventId).getEventName()
                    : "Not Found";

            tableModel.addRow(new Object[] {
                    reg.getRegistrationId(),
                    userId,
                    userName,
                    eventId,
                    eventName,
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

    public JButton getDeleteButton() {
        return deleteButton;
    }

    @Override
    public void updateRegistration(List<EventRegistration> registrations) {
        updateTable(registrations);
    }

    @Override
    public void updateEvent(List<Event> events) {
        updateTable(RegistrationManager.getInstance().getRegistrations());
    }

    @Override
    public void updateUser(List<User> events) {
        updateTable(RegistrationManager.getInstance().getRegistrations());
    }

}
