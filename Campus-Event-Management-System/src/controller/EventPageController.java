package controller;

import javax.swing.JOptionPane;
import model.Event;
import model.manager.EventManager;
import model.manager.RegistrationManager;
import view.EventDialog;
import view.EventPageView;

public class EventPageController {
    private final EventManager eventManager;
    private final EventPageView view;

    public EventPageController(EventPageView view) {
        this.eventManager = EventManager.getInstance();
        this.view = view;

        // this.eventManager.registerObserver(view);
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
        // Get registration count for this event
        RegistrationManager regManager = RegistrationManager.getInstance();
        long registrationsCount = regManager.getRegistrations().stream()
                .filter(reg -> reg.getEventId().equals(selectedEvent.getEventId()))
                .count();

        EventDialog dialog = new EventDialog("Update Event", selectedEvent);

        dialog.setConfirmActionListener(e -> {
            try {
                Event updatedFields = dialog.getEventFromFields();

                // Calculate remaining capacity = eventCapacity - registered users count
                int remainingCapacity = updatedFields.getEventCapacity() - (int) registrationsCount;
                if (remainingCapacity < 0)
                    remainingCapacity = 0;

                Event updatedEvent = new Event.EventBuilder(selectedEvent)
                        .eventName(updatedFields.getEventName())
                        .eventDate(updatedFields.getEventDate())
                        .eventVenue(updatedFields.getEventVenue())
                        .eventType(updatedFields.getEventType())
                        .currentCapacity(remainingCapacity)
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
