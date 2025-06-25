package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Event;
import model.observer.EventObserver;
import shared.Navigation;

public class EventPageView extends PageView implements EventObserver {

    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;

    public EventPageView() {
        super("Event Management Page");
        setVisible(true);
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Name", "Date", "Venue", "Type", "Capacity", "Fee" };
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(eventTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("Home");
        this.createButton = new JButton("Create");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");

        buttonPanel.add(homeButton);
        buttonPanel.add(this.createButton);
        buttonPanel.add(this.updateButton);
        buttonPanel.add(this.deleteButton);
        // Button Action Listener
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    // action listener will be added to controller since more complicated
    public JButton getUpdateButton() {
        return this.updateButton;
    }

    public JButton getCreateButton() {
        return this.createButton;
    }

    public JButton getDeleteButton() {
        return this.deleteButton;
    }

    public void updateTable(List<Event> events) {
        tableModel.setRowCount(0); // Clear table
        for (Event e : events) {
            tableModel.addRow(new Object[] {
                    e.getEventName(),
                    new SimpleDateFormat("yyyy-MM-dd").format(e.getEventDate()),
                    e.getEventVenue(),
                    e.getEventType(),
                    e.getEventCapacity(),
                    e.getRegistrationFee() == 0 ? "Free" : "RM" + e.getRegistrationFee()
            });
        }
    }

    // get the selected row
    public int getSelectedRow() {
        return eventTable.getSelectedRow();
    }

    // return the Event object of the selected row
    public Event getSelectedEvent() {
        int row = eventTable.getSelectedRow();
        if (row == -1)
            return null;

        try {
            String name = tableModel.getValueAt(row, 0).toString();
            String date = tableModel.getValueAt(row, 1).toString();
            String venue = tableModel.getValueAt(row, 2).toString();
            String type = tableModel.getValueAt(row, 3).toString();
            int capacity = (int) tableModel.getValueAt(row, 4);

            String feeStr = tableModel.getValueAt(row, 5).toString().trim();
            int fee = feeStr.equalsIgnoreCase("Free") ? 0 : Integer.parseInt(feeStr.replaceAll("[^\\d]", ""));

            return new Event.EventBuilder()
                    .eventName(name)
                    .eventDate(new SimpleDateFormat("yyyy-MM-dd").parse(date))
                    .eventVenue(venue)
                    .eventType(type)
                    .eventCapacity(capacity)
                    .registrationFee(fee)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // update function if changes
    @Override
    public void update(List<Event> events) {
        updateTable(events);
    }
}
