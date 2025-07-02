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
        view.getDeleteButton().addActionListener(e -> deleteRegistration());
    }

    private void refreshTable() {
        List<EventRegistration> registrations = registrationManager.getRegistrations();
        view.updateRegistration(registrations);
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

        registrationManager.removeRegistration(regId);
        JOptionPane.showMessageDialog(view, "Registration deleted successfully!");
        refreshTable();

    }
}
