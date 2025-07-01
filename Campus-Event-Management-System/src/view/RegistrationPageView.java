package view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Event;

public class RegistrationPageView extends PageView {
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
        eventList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Event selectedEvent = eventList.getSelectedValue();
                if (selectedEvent != null) {
                    displayEventDetails(selectedEvent.getDetails());
                } else {
                    displayEventDetails("");
                }
            }
        });

        panel.add(new JScrollPane(eventList), BorderLayout.WEST);

        // Event details area (center)
        eventDetailsArea = new JTextArea(10, 30);
        eventDetailsArea.setEditable(false);
        panel.add(new JScrollPane(eventDetailsArea), BorderLayout.CENTER);

        // Register button at bottom
        registerButton = new JButton("Register for this Event");
        panel.add(registerButton, BorderLayout.SOUTH);

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
}
