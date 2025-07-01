package controller;

import java.util.List;
import javax.swing.*;
import model.EventRegistration;
import model.manager.RegistrationManager;
import view.ReportsPageView;

public class ReportsPageController {

    private final ReportsPageView view;
    private final RegistrationManager registrationManager;

    public ReportsPageController(ReportsPageView view) {
        this.view = view;
        this.registrationManager = RegistrationManager.getInstance();

        setupListeners();
        refreshTable();
    }

    private void setupListeners() {
        // No create button listener since create is disabled or removed
        view.getUpdateButton().addActionListener(e -> updateRegistration());
        view.getDeleteButton().addActionListener(e -> deleteRegistration());
        view.getRefreshButton().addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        List<EventRegistration> registrations = registrationManager.getRegistrations();
        view.update(registrations);
    }

    private void updateRegistration() {
        String regId = view.getSelectedRegistrationId();
        if (regId == null) {
            JOptionPane.showMessageDialog(view, "Please select a registration to update.");
            return;
        }

        EventRegistration registration = registrationManager.getRegistrationById(regId);
        if (registration == null) {
            JOptionPane.showMessageDialog(view, "Selected registration not found.");
            return;
        }

        try {
            String status = JOptionPane.showInputDialog(view, "Update Status:", registration.getStatus());
            if (status == null || status.trim().isEmpty())
                return;

            String paymentStr = JOptionPane.showInputDialog(view, "Update Payment Amount:",
                    registration.getPaymentAmount());
            if (paymentStr == null || paymentStr.trim().isEmpty())
                return;

            double paymentAmount = Double.parseDouble(paymentStr);

            // Build updated registration (assuming immutability)
            EventRegistration updated = new EventRegistration.EventRegistrationBuilder()
                    .registrationId(registration.getRegistrationId())
                    .userId(registration.getUserId())
                    .eventId(registration.getEventId())
                    .registrationDate(registration.getRegistrationDate())
                    .status(status.trim())
                    .paymentAmount(paymentAmount)
                    .build();

            // Replace old registration in map
            registrationManager.getRegistrations().remove(registration);
            registrationManager.getRegistrations().add(updated);

            // Save all to file (overwrite)
            registrationManager.saveAllRegistrationsToFile();

            JOptionPane.showMessageDialog(view, "Registration updated successfully!");
            refreshTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error updating registration: " + ex.getMessage());
        }
    }

    private void deleteRegistration() {
        String regId = view.getSelectedRegistrationId();
        if (regId == null) {
            JOptionPane.showMessageDialog(view, "Please select a registration to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this registration?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean removed = registrationManager.getRegistrations().removeIf(reg -> reg.getRegistrationId().equals(regId));
        if (removed) {
            registrationManager.saveAllRegistrationsToFile();

            JOptionPane.showMessageDialog(view, "Registration deleted successfully!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to delete registration.");
        }
    }
}
