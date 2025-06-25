package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.Event;
import model.EventManager;
import view.EventDialog;
import view.EventPageView;

// TODO Update add functinality to buttons
public class EventPageController {
    private final EventManager eventManager;
    private final EventPageView view;

    public EventPageController(EventManager eventManager, EventPageView view) {
        this.eventManager = eventManager;
        this.view = view;
        setupEventListeners();
    }

    public void loadAndDisplayEvents() {
        List<Event> events = eventManager.getEvents();
        view.updateTable(events);
    }

    private void setupEventListeners() {
        // Create button listener
        view.getCreateButton().addActionListener(e -> {
            showCreateDialog();
        });

        // Update button listener
        view.getUpdateButton().addActionListener(e -> {
            Event selected = view.getSelectedEvent();
            if (selected == null) {
                JOptionPane.showMessageDialog(view, "Please select an event to update",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showUpdateDialog(selected);
        });

        // Delete button listener
        view.getDeleteButton().addActionListener(e -> {
            Event selected = view.getSelectedEvent();
            if (selected == null) {
                JOptionPane.showMessageDialog(view, "Please select an event to delete",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            confirmAndDeleteEvent(selected);
        });
    }

    private void showCreateDialog() {
        // Create a custom dialog for event creation
        EventDialog dialog = new EventDialog("Create New Event", null);
        dialog.setConfirmActionListener(e -> {
            try {
                Event newEvent = dialog.getEventFromFields();
                eventManager.addEvent(newEvent);
                loadAndDisplayEvents();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.setVisible(true);
    }

    private void showUpdateDialog(Event selectedEvent) {
        // Create a custom dialog pre-filled with selected event data
        EventDialog dialog = new EventDialog("Update Event", selectedEvent);
        dialog.setConfirmActionListener(e -> {
            try {
                Event updatedEvent = dialog.getEventFromFields();
                // Preserve the original event ID
                // updatedEvent = new Event.EventBuilder(updatedEvent)
                // .eventId(selectedEvent.getEventId())
                // .build();
                System.out.println(updatedEvent);
                eventManager.updateEvent(selectedEvent.getEventId(), updatedEvent);
                loadAndDisplayEvents();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.setVisible(true);
    }

    private void confirmAndDeleteEvent(Event event) {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete:\n" + event.getEventName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            eventManager.removeEvent(event.getEventId());
            loadAndDisplayEvents();
        }
    }
}
