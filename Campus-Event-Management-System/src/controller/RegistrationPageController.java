package controller;

import java.util.List;
import javax.swing.*;
import model.*;
import view.RegistrationPageView;
import view.UserRegistrationDialog;

public class RegistrationPageController {
    private final RegistrationPageView eventView;
    private final EventManager eventManager;
    private final UserManager userManager;
    private Event selectedEvent;

    public RegistrationPageController(EventManager eventManager,
            RegistrationPageView eventView) {
        this.eventManager = eventManager;
        this.eventView = eventView;
        this.userManager = UserManager.getInstance();

        setupEventListeners();
        loadEvents();
    }

    private void setupEventListeners() {
        // Register button clicked
        eventView.getRegisterButton().addActionListener(e -> {
            selectedEvent = eventView.getSelectedEvent();
            if (selectedEvent == null) {
                JOptionPane.showMessageDialog(eventView, "Please select an event first.");
                return;
            }

            // Show user registration dialog
            UserRegistrationDialog registrationDialog = new UserRegistrationDialog(
                    "Register for " + selectedEvent.getEventName());
            registrationDialog.setConfirmActionListener(confirmEvent -> {
                try {
                    User user = registrationDialog.getUserFromFields();

                    // Check if user already exists
                    User existingUser = userManager.getUserFromId(user.getUserId());
                    if (existingUser == null) {
                        // New user: add to UserManager
                        userManager.addUser(user);
                        existingUser = user;
                    }

                    // Register user for the selected event
                    registerUserForEvent(existingUser);

                    // Close the dialog after successful registration
                    registrationDialog.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(registrationDialog,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            registrationDialog.setVisible(true);
        });
    }

    private void loadEvents() {
        List<Event> events = eventManager.getEvents();
        eventView.displayEvents(events);
    }

    private void registerUserForEvent(User user) {
        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(null, "No event selected.");
            return;
        }

        Registration registration = new Registration.RegistrationBuilder()
                .userId(user.getUserId())
                .eventId(selectedEvent.getEventId())
                .build();

        Bill bill = new Bill(
                selectedEvent.getRegistrationFee(),
                0, // no services selected in this simplified flow
                0);

        JOptionPane.showMessageDialog(null,
                "Registration successful for " + user.getName() +
                        "\nEvent: " + selectedEvent.getEventName() +
                        "\nBill:\n" + bill.getBreakdown(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}