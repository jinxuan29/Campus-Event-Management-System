package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.CulturalEvent;
import model.Event;
import model.Seminar;
import model.SportEvent;
import model.Workshop;
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

        String[] columns = { "ID", "Name", "Date", "Venue", "Type", "Current", "Capacity", "Fee" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);
        eventTable.setRowSelectionAllowed(true);
        eventTable.setColumnSelectionAllowed(false);

        JScrollPane scrollPane = new JScrollPane(eventTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("Home");
        this.createButton = new JButton("Create");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");

        buttonPanel.add(this.createButton);
        buttonPanel.add(this.updateButton);
        buttonPanel.add(this.deleteButton);
        buttonPanel.add(homeButton);
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

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
        tableModel.setRowCount(0);
        for (Event e : events) {
            tableModel.addRow(new Object[] {
                    e.getEventId(),
                    e.getEventName(),
                    new SimpleDateFormat("yyyy-MM-dd").format(e.getEventDate()),
                    e.getEventVenue(),
                    e.getEventType(),
                    e.getCurrentCapacity(),
                    e.getEventCapacity(),
                    e.getRegistrationFee() == 0 ? "Free" : "RM" + e.getRegistrationFee()
            });
        }
    }

    public int getSelectedRow() {
        return eventTable.getSelectedRow();
    }

    public Event getSelectedEvent() {
        int row = eventTable.getSelectedRow();
        if (row == -1)
            return null;

        try {
            String id = tableModel.getValueAt(row, 0).toString();
            String name = tableModel.getValueAt(row, 1).toString();
            String dateStr = tableModel.getValueAt(row, 2).toString();
            String venue = tableModel.getValueAt(row, 3).toString();
            String type = tableModel.getValueAt(row, 4).toString().toLowerCase();
            int currentCapacity = Integer.parseInt(tableModel.getValueAt(row, 5).toString());
            int capacity = Integer.parseInt(tableModel.getValueAt(row, 6).toString());
            String feeStr = tableModel.getValueAt(row, 7).toString().trim();
            int fee = feeStr.equalsIgnoreCase("Free") ? 0 : Integer.parseInt(feeStr.replaceAll("[^\\d]", ""));
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            switch (type) {
                case "seminar":
                    return new Seminar.SeminarBuilder()
                            .eventId(id)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                case "workshop":
                    return new Workshop.WorkshopBuilder()
                            .eventId(id)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                case "cultural":
                    return new CulturalEvent.CulturalEventBuilder()
                            .eventId(id)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                case "sport":
                    return new SportEvent.SportEventBuilder()
                            .eventId(id)
                            .eventName(name)
                            .eventDate(date)
                            .eventVenue(venue)
                            .currentCapacity(currentCapacity)
                            .eventCapacity(capacity)
                            .registrationFee(fee)
                            .build();
                default:
                    throw new IllegalArgumentException("Unknown event type: " + type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(List<Event> events) {
        updateTable(events);
    }
}
