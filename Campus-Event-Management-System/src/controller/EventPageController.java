package controller;

import javax.swing.JOptionPane;
import model.Event;
import model.EventManager;
import view.EventDialog;
import view.EventPageView;

public class EventPageController {
    private final EventManager eventManager;
    private final EventPageView view;

    public EventPageController(EventManager eventManager, EventPageView view) {
        this.eventManager = eventManager;
        this.view = view;

        this.eventManager.registerObserver(view);
        this.eventManager.eventsUpdated();

        setupEventListeners();
    }

    private void setupEventListeners() {
        // Create button listener
        view.getCreateButton().addActionListener(e -> showCreateDialog());

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
        EventDialog dialog = new EventDialog("Create New Event", null);

        dialog.setConfirmActionListener(e -> {
            try {
                Event newEvent = dialog.getEventFromFields();
                eventManager.addEvent(newEvent); // Will trigger observer update
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void showUpdateDialog(Event selectedEvent) {
        EventDialog dialog = new EventDialog("Update Event", selectedEvent);

        dialog.setConfirmActionListener(e -> {
            try {
                Event updatedFields = dialog.getEventFromFields();
                Event updatedEvent = new Event.EventBuilder(selectedEvent) // existing event as base
                        .eventName(updatedFields.getEventName())
                        .eventDate(updatedFields.getEventDate())
                        .eventVenue(updatedFields.getEventVenue())
                        .eventType(updatedFields.getEventType())
                        .eventCapacity(updatedFields.getEventCapacity())
                        .registrationFee(updatedFields.getRegistrationFee())
                        .build();
                eventManager.updateEvent(selectedEvent.getEventId(), updatedEvent);
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
        }
    }
}
