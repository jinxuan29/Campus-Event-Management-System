package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import model.Event;
import model.observer.EventObserver;
import shared.Navigation;

public class RegistrationPageView extends PageView implements EventObserver {
    private JList<Event> eventList;
    private JButton registerButton;
    private JTextArea eventDetailsArea;

    public RegistrationPageView() {
        super("Select Event");
    }

    @Override
    protected JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Event list
        eventList = new JList<>();
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventList.addListSelectionListener((ListSelectionEvent e) -> {
            Event selectedEvent = eventList.getSelectedValue();
            if (selectedEvent != null) {
                displayEventDetails(selectedEvent.getDetails());
            } else {
                displayEventDetails("");
            }
        });

        panel.add(new JScrollPane(eventList), BorderLayout.WEST);

        // Event details area (center)
        eventDetailsArea = new JTextArea(10, 30);
        eventDetailsArea.setEditable(false);
        panel.add(new JScrollPane(eventDetailsArea), BorderLayout.CENTER);

        // Create button panel for bottom right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // Register button
        registerButton = new JButton("Register for this Event");
        registerButton.setPreferredSize(new Dimension(180, 30));

        // Home button
        JButton homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 30));
        homeButton.addActionListener(e -> Navigation.navigateTo("HOME"));

        // Add buttons to button panel
        buttonPanel.add(registerButton);
        buttonPanel.add(homeButton);

        // Add button panel to main panel (SOUTH)
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void displayEvents(List<Event> events) {
        DefaultListModel<Event> model = new DefaultListModel<>();
        for (Event event : events) {
            model.addElement(event);
        }
        eventList.setModel(model);
    }

    public Event getSelectedEvent() {
        return eventList.getSelectedValue();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void displayEventDetails(String details) {
        eventDetailsArea.setText(details);
    }

    // update function if changes
    @Override
    public void update(List<Event> events) {
        displayEvents(events);
    }
}
