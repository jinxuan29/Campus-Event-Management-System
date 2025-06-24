package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import shared.Navigation;

public class EventPageView extends PageView {

    private JTable eventTable;
    private DefaultTableModel tableModel;

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
        JButton createBtn = new JButton("Create");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(homeButton);
        buttonPanel.add(createBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Button Action Listener
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add dummy data (can be replaced with real model later)
        tableModel.addRow(new Object[] { "AI Seminar", "2025-07-10", "Auditorium", "Seminar", 100, "RM20" });
        tableModel.addRow(new Object[] { "Cultural Fest", "2025-08-15", "Main Hall", "Cultural Event", 300, "Free" });

        return contentPanel;
    }
}
